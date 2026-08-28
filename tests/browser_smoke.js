/**
 * Headless-browser smoke test for the Eaglercraft 26.2 TeaVM JS client.
 *
 * Verifies that dist/client actually boots in a real (headless) Chromium:
 *  - classes.js parses and executes
 *  - the entry point runs without throwing
 *  - the demo renderer writes to a canvas
 *  - no uncaught page exceptions / broken resources are observed
 *
 * Run:  node tests/browser_smoke.js [path-to-chrome]
 */
const path = require('path');
const { readFileSync, existsSync } = require('fs');
const puppeteer = require('puppeteer-core');

const DIST = path.resolve(__dirname, '../dist/client');
const INDEX_HTML = path.join(DIST, 'index.html');
const INDEX = 'file://' + INDEX_HTML;

const DEFAULT_CHROME =
	'/home/nixel_tide/.cache/puppeteer/chrome/linux-152.0.7977.42/chrome-linux64/chrome';

function chromePath() {
	const arg = process.argv[2];
	if (arg) return arg;
	return existsSync(DEFAULT_CHROME) ? DEFAULT_CHROME : null;
}

(async () => {
	const executable = chromePath();
	if (!executable) {
		console.error('SKIP: no chrome binary found; pass a path as argv[2]');
		process.exit(0);
	}
	if (!existsSync(path.join(DIST, 'classes.js')) || !existsSync(INDEX_HTML)) {
		console.error('FAIL: client not built. Run: ./gradlew :target_teavm_javascript:buildEaglerJS');
		process.exit(1);
	}

	const errors = [];
	const logs = [];
	const browser = await puppeteer.launch({
		executablePath: executable,
		headless: 'shell',
		args: ['--no-sandbox', '--disable-setuid-sandbox', '--window-size=1280,720'],
	});

	try {
		const page = await browser.newPage();
		await page.setViewport({ width: 1280, height: 720 });

		page.on('pageerror', (e) => errors.push('PAGE ERROR: ' + e.message));
		page.on('console', (msg) => {
			const t = msg.text();
			logs.push(t);
			if (msg.type() === 'error') errors.push('CONSOLE ERROR: ' + t);
		});
		page.on('requestfailed', (r) =>
			errors.push('REQUEST FAILED: ' + r.url() + ' ' + (r.failure() && r.failure().errorText)));

		await page.goto(INDEX, { waitUntil: 'load', timeout: 30000 });

		// give rAF-driven renderer a moment to draw several frames
		await new Promise((r) => setTimeout(r, 3000));

		const result = await page.evaluate(() => {
			const canvas = document.getElementById('game-canvas');
			if (!canvas) return { hasCanvas: false };
			const ctx = canvas.getContext('2d');
			// read a pixel to confirm the demo actually painted (not blank)
			let nonBlack = false;
			try {
				const data = ctx.getImageData(0, 0, canvas.width, canvas.height).data;
				for (let i = 0; i < data.length; i += 4) {
					if (data[i] || data[i + 1] || data[i + 2]) { nonBlack = true; break; }
				}
			} catch (e) {
				return { hasCanvas: true, readError: e.message };
			}
			const title = document.title;
			return { hasCanvas: true, nonBlack, title, w: canvas.width, h: canvas.height };
		});

		console.log('=== CLIENT BOOT RESULT ===');
		console.log(JSON.stringify(result, null, 2));
		console.log('=== CONSOLE (first 30) ===');
		console.log(logs.slice(0, 30).join('\n'));
		console.log('=== ERRORS ===');
		const errs = errors.filter((e) => /error/i.test(e));
		if (errs.length === 0) console.log('(none)');

		const hardFailures = errors.length;
		const booted = result && result.hasCanvas;
		const painted = result && result.nonBlack;

		console.log('\n=== SMOKE VERDICT ===');
		console.log('canvas present :', booted ? 'PASS' : 'FAIL');
		console.log('demo painted   :', painted ? 'PASS' : 'FAIL');
		console.log('uncaught errors:', hardFailures === 0 ? 'PASS (' + hardFailures + ')' : 'FAIL (' + hardFailures + ')');

		const ok = booted && painted && hardFailures === 0;
		console.log(ok ? '\nOVERALL: PASS' : '\nOVERALL: FAIL');
		process.exit(ok ? 0 : 2);
	} finally {
		await browser.close();
	}
})();
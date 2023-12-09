# Node JS for TS setup....
Even C with Makefiles is easier to set up!

```bash
1. npm init -y
2. npm install typescript --save-dev
3. npm install @types/node --save-dev
4. npx tsc --init --rootDir src --outDir lib --esModuleInterop --resolveJsonModule --lib es6,dom  --module commonjs
5. npm install ts-node --save-dev
6. npm install nodemon --save-dev
7. Script target similar to this:
   "scripts": {
      "start": "npm run build:live",
      "build": "tsc -p .",
      "build:live": "nodemon --watch '*.ts' --exec \"ts-node\" src.ts"
   },
8. npm start
```
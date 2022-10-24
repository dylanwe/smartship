/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./public/**/*.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      strokeWidth: {
        '1.5': '1.5px',
      }
    },
  },
  plugins: [],
}

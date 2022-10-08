/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./public/**/*.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      strokeWidth: {
        '2': '1.5px',
      }
    },
  },
  plugins: [],
}

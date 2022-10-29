/** @type {import('tailwindcss').Config} */
const colors = require("tailwindcss/colors");
module.exports = {
  content: ['./public/**/*.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        primary: colors.sky,
        neutral: colors.slate,
      },
      strokeWidth: {
        '1.5': '1.5px',
      }
    },
  },
  plugins: [],
}

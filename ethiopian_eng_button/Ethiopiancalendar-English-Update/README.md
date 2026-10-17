# Ethiopian Calendar (Android)

A minimal Android Ethiopian calendar with Amharic and English display modes.

## Language toggle
- The small `EN` button in the top-right switches the interface to English.
- In English mode, the same button shows `AM` to switch back to Amharic.
- Your language choice is saved between launches.
- English mode uses English weekday names, Gregorian dates, controls, and holiday names. Ethiopian month names are written in Latin letters with a short bracketed note to help newcomers understand each month's place in the calendar. `Pagume` is described as the additional-days month.
- The language toggle changes display labels only; it does not change calendar calculations.

## Features
- Ethiopian calendar with 13 months.
- Ethiopian day numbers with Gregorian day numbers in smaller text.
- Previous/next month navigation and a Today button.
- Tap a date to view its Ethiopian and Gregorian equivalents.
- Major holidays marked in the calendar and shown for the selected date.
- Light and dark themes follow the phone's system setting.

## Build the APK in GitHub Actions (no powerful PC required)
1. Push this repository to GitHub.
2. Open the repository's **Actions** tab and open the latest **Build APK** run.
3. Wait for the workflow to finish successfully.
4. Under **Artifacts**, download `EthiopianCalendar-APK`, unzip it, and copy the APK to your phone.
5. If Android blocks installation, allow installs from the app you used to open the APK.

## Push changes from an existing local clone
Run these commands from the folder containing this project's `.git` directory:

```bash
git status
git add .
git commit -m "Add compact English language toggle"
git push origin main
```

If your branch is not `main`, check it with `git branch --show-current` and push that branch instead. Do not run `git init` in an existing repository.

## Important note about holidays
This is a short list of major holidays. It does not include Islamic holidays (Eid al-Fitr, Eid al-Adha, or Mawlid), which follow the lunar calendar. Government-announced dates may also differ in a given year. Check an official calendar before relying on a holiday date.

## Main source files
- `app/src/main/java/com/example/ethiopiancalendar/EthiopianCalendar.kt`: date conversion.
- `app/src/main/java/com/example/ethiopiancalendar/Labels.kt`: Amharic and English labels.
- `app/src/main/java/com/example/ethiopiancalendar/Holidays.kt`: holiday list.
- `app/src/main/java/com/example/ethiopiancalendar/MainActivity.kt`: app interface.
- `.github/workflows/build.yml`: cloud APK build.

# Ethiopian Calendar (Android)

A simple Android calendar app that shows the Ethiopian calendar (13 months), with:

- Month view with Ethiopian day numbers and the Gregorian day in small print
- Month names and weekday names in Amharic and English
- Previous / next month buttons and a "Today" button
- Tap any day to see its full Ethiopian and Gregorian date
- Major public holidays marked with a dot (see note below)
- Light and dark mode (follows your phone's setting)

## Getting the app onto your phone (no heavy PC needed)

GitHub builds the app for you on its own servers.

1. Put this folder on GitHub (see below).
2. On your repository page, open the **Actions** tab. A run called **Build APK** starts by itself.
   Wait for the green tick (about 5 to 10 minutes the first time).
3. Click the finished run, scroll to **Artifacts**, and download **EthiopianCalendar-APK**.
4. Unzip it, copy the `.apk` file to your phone, and tap it to install.
   Your phone will ask you to allow installs from this source the first time.

If the run shows a red cross, open it, copy the error text, and send it to Claude.

## Putting the folder on GitHub

With the GitHub command-line tool (easiest):

```bash
sudo pacman -S github-cli
gh auth login
cd EthiopianCalendar
git init -b main
git add .
git commit -m "First version"
gh repo create EthiopianCalendar --public --source=. --push
```

`gh auth login` asks a few questions; choose GitHub.com, HTTPS, and log in through the browser.

Keep the repository **public**: free builds on public repositories are not limited.
Do not put passwords or personal information in it.

## Note about holidays

The holiday list is a short list of major holidays. It does not include the Islamic holidays
(Eid al-Fitr, Eid al-Adha, Mawlid), which follow the moon. Holidays announced by the government
can also differ from the list in a given year. Please check it against an official calendar
before relying on it.

## Where things are

- `app/src/main/java/com/example/ethiopiancalendar/EthiopianCalendar.kt`: date conversion
- `app/src/main/java/com/example/ethiopiancalendar/Holidays.kt`: holiday list
- `app/src/main/java/com/example/ethiopiancalendar/MainActivity.kt`: the screen
- `.github/workflows/build.yml`: tells GitHub how to build the app

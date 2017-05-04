# ![](https://ga-dash.s3.amazonaws.com/production/assets/logo-9f88ae6c9c3871690e33280fcf557f33.png) Project #3

#### Overview

##### Team members - Will, Ivonne, Sam

Our app for this project is a news reader based on the New York Times API per the following specifications:

* [CNN](CNN)

Users can view news as it comes out based on category, and they can view top news items in a continuous stream. Users can also share articles via social media. The app periodically checks for new articles, and the user can also manually check for updates.

---

#### Functionality

When the app first opens, it takes the user to a loading page, which checks the server for new articles, which will be available for reading once the check is complete. This check can take additional time based on how long it has been since the last load. Articles downloaded during this check are stored in a database for quick access. Part of the download process checks to see whether articles are already in the database, so as not to add duplicates.

Once the initial load has been completed the user is taken to the main activity, which has three primary tabs in a `ViewPager`: All stories, Top stories, and Save. All stories contains categorized horizontal scrolling `RecyclerView`s of news thumbnails, all nested within a standard vertical scrolling `RecyclerView`.

The Top stories tab contains a single `RecyclerView` containing all of the collected top news items. Each item in this tab displays a thumbnail, the title, the date of publication, the category, and buttons for sharing and saving. Tapping the save button, shaped like a heart, adds the article to the saved articles list, while tapping the share button brings up a share using chooser.

The Save tab contains a list of all items the user has saved. Tapping the share button brings up a share using chooser. Swiping left on an article in this view removes it from the saved list. Removing an article from the saved list brings up a `SnackBar` giving the user the option to undo this action.

Detail view, web view

Categories

Navigation drawer

Sharing

Notification

Search

Settings

---

#### Screenshots


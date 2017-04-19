# ![](https://ga-dash.s3.amazonaws.com/production/assets/logo-9f88ae6c9c3871690e33280fcf557f33.png) Project #3: Yelp -- New Local Experiences App

#### Overview

Project 3 is your opportunity to really let your creativity shine! You will have the basic app requirements, provided by Yelp, but it is up to you to design everything. For this project, your job will be to build an Android app that enables users to discover great local businesses in fun, new ways. The big idea is to create a sense of serendipity and delight for users, ideally including a social component. Here are some ideas for your app:

- Employ gamification to find the best local business (tacos/Italian/bars/etc.)
- Deliver recommendations for an amazing night out when attending a concert or show
- Drive Eat24 food orders and/or SeatMe reservations through an embeddable widget
- Deliver Yelp recommendations through a messaging experience

---

#### Requirements / Constraints

Your work must:

- Call the Yelp API (see below for a full list of available endpoints and categories), which provides access to a large subset of Yelp’s content in JSON format, including a review snippet, review or business thumbnail, business name, address, phone number, star rating, number of reviews, and much more	
- Allow the user to quickly view businesses and, as a result, opportunities for discovery, through a richly visual and geographically-relevant presentation	
- Adhere to Yelp’s [Display Requirements](https://www.yelp.com/developers/display_requirements) and [Terms of Use](https://www.yelp.com/developers/api_terms)	
- Give your app a new, interesting twist by creating a mashup with at least one other API. APIs you might consider using include:	
	- Facebook
	- Twitter
	- Twilio
	- Pinterest
	- Airbnb
	- Uber/Lyft
- Include **at least 2 prototypes**
- Include user stories based on your research and feature prioritization in a Trello board for the **complete flow of your app**
- Allow sharing of content to social media apps including Twitter and/or Facebook
- Look great in both landscape and portrait modes and reflect Material Design principles
- Include at least one **Notification** feature (e.g. reminder, alarm)
- Include **automated testing** (and manual testing if needed) to cover your app.
- Use **JobScheduler** to manage periodic background API calls
- Not crash or hang and should handle for when networking/internet is slow or unavailable
- Have code that is semantically clean and well-organized


**Bonus:**

- Focus on businesses which provide food delivery via Yelp Eat24 or table reservations through Yelp SeatMe
- Integrate with a mapping and/or navigation API
- Integrate with a messaging/communication API such as Slack
- Allow the user to save, bookmark, or ‘like’ a new discovery from within the app (for clarity: this does not mean creating or posting content on Yelp via the API, but rather standalone functionality built into your app)

---

#### Code of Conduct

As always, your app must adhere to General Assembly's [student code of conduct guidelines](https://ga-adi.gitbooks.io/adi-oreo/content/markdown/code-of-conduct.html).

If you have questions about whether or not your work adheres to these guidelines, please speak with a member of your instructional team.

---

#### Necessary Deliverables

**Project planning:**
- A completed research plan according to the [template provided in class](../worksheets/research-plan-worksheet.pdf)
- Competitive research in a Google Sheet
- Written [user personas](../worksheets/persona-worksheet.pdf) (based on your [synthesis](../worksheets/design-thinking-activity-worksheet.pdf) of your [research findings](../worksheets/research-highlights-worksheet.pdf))
- A list of prioritized features
- A project plan presentation (there will be an in-class lab for this)
- Completed user stories
- A link to your team's Trello Board set up according to [Trello's suggestions](http://buildbettersoftware.com/with-trello/)

**App:**
- A final, working version of your app
- Working cloud synchronization using JobScheduler and API integration
- A **git repository hosted on GitHub**, with frequent commits dating back to the **very beginning** of the project. Commit early, commit often
- **A `readme.md` file** describing what the app does, and any bugs that may exist
- At least one screenshot in the `readme.md`
- Automated tests for your code - unit tests and Espresso tests

---

#### Suggested Ways to Get Started

Visit http://yelp.com/developers and apply for API access [here](https://www.yelp.com/developers/v3/manage_app) using a Yelp user account.

- Think about a user experience which doesn’t create already-available Yelp functionality.
- Complete as much of the layout as possible before working on your logic.
- Write pseudocode before you write actual code. Thinking through the logic helps.
- Test functionality as soon as you complete it. *Don't start working on a new feature until all the existing features are working!*

---

### Useful Resources

- [Documentation](https://www.yelp.com/developers/documentation/v3/)
- [Display requirements](https://www.yelp.com/developers/display_requirements)
- [Code samples](https://github.com/Yelp/yelp-fusion#code-samples)
- [FAQ](https://www.yelp.com/developers/faq) & [GitHub](https://github.com/Yelp/yelp-api/issues)
- [Terms of Use](https://www.yelp.com/developers/api_terms)
- [Directory of APIs](http://www.programmableweb.com/apis/directory)
- [Android API Reference](http://developer.android.com/reference/packages.html)
- [Android API Guides](http://developer.android.com/guide/index.html)
- [Twitter for Developers](https://dev.twitter.com/)
- [Facebook for Developers](https://developers.facebook.com/)

### Full List of Yelp Categories
Please refer to Yelp’s [full category list here](https://www.yelp.com/developers/documentation/v3/all_category_list). They’re much too extensive to place in this brief!

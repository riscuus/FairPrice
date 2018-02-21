# FairPrice
The Fair Price App is an Android based game, it has 3 modes of game: Easy, Hard and Challenge. Its principal features are:

- It is connected to an online Firebase Database in which there are more or less 200 products whith their original prices from Amazon.com.

- All modes of game have a timer. The user has survive the maximum possible with the number of lifes he has.

##### Easy Mode
- When the Easy mode is initializated it downloads a product from the database. The app downloads its name, its photo, its original price and creates 3 more random prices similar to the original one. If the user chooses the original product it downloads the following product, if not the user loses a life.

##### Hard Mode
- When the Hard mode is initializated it downloads a product from the database. The app downloads its name, its photo, its original price and the user has to guess it and write it down on a custom keyboard. If the price writed from the user is more or less similar to the original one the app downloads the following product, if not the user loses a life.

##### Challenge Mode
- When the Challenge mode is initializated it downloads 2 products from the database. The user has to guess if the product that is on the bottom half of the screen costs less or more than the other product. If he doesn't guess well he loses.



## future features
- The user will be able to connect to the google game services and save its progress.
- The user will be able to buy life in the game.
- The user will be able to adjust some settings as: the lenguage, the currency, etc.
- Improvements in the interface and addition of animations.

# Getting Started
./gradlew BootRun will start the app on port 8080.
By default the items and prices will be set to the same defined in the spec.

### Basket
##### Create basket
This will instantiate an empty basket. All future calls will modify this basket
* POST http://localhost:8080/basket

##### Add to basket
This will add an item to the basket. Item ID must exist in the price rules to work (See below price section)
* PUT http://localhost:8080/basket/{itemId}
* for example http://localhost:8080/basket/A

##### Get basket with value
This will retrieve the basket with all items and the total price, including discounts where applicable.
* GET http://localhost:8080/basket

### Price
This modifies existing rules and adds new rules at runtime.
In the below example, you could purchase 5 of item 'A' for 150
* POST http://localhost:8080/price
* JSON Body {
            	"itemId": "A",
            	"price": 50,
            	"discountPrice": 30,
            	"discountBulk": 5
            }


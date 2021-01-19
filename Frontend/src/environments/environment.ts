// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  uriPrefix: 'http://localhost:8080/api/',
  imageUrlString: '/assets/product-images/',
  testProducts: [
    {
      productId: 1,
      price: 14999.0,
      imagePath: 'rolex-daydate-goud.jpg',
      unitsSold: 0,
      name: 'Day-Date',
      description: 'Gouden Rolex Day-Date met groene wijzerplaat. Dagen in het Engels',
      stock: 1,
      brand: 'Rolex'
    },
    {
      productId: 2,
      price: 179.0,
      imagePath: 'seiko-5-staal.jpg',
      unitsSold: 20,
      name: '5 SNKD99K1',
      description: 'Stalen Seiko 5 met datum en dag van de week (Engels en Spaans). Waterdicht tot 50 meter.',
      stock: 10,
      brand: 'Seiko'
    },
    {
      productId: 3,
      price: 179.0,
      imagePath: 'paulhewitt-sailor-zwart.jpg',
      unitsSold: 50,
      name: 'Sailor Black Sunray',
      description: 'Zwart stalen dress-watch van Paul-Hewitt kast is het sailor model met een zwart stalen mesh band. Met gratis armband (zwart nylon touw met zwart stalen anker)',
      stock: 100,
      brand: 'Paul-Hewitt'
    },
    {
      productId: 4,
      price: 24999.0,
      imagePath: 'ap-royaloak15400-staal.jpg',
      unitsSold: 2,
      name: 'Royal Oak Ref 15400',
      description: 'stalen ap royal-oak referentie 15400. Waterbestendigheid: 5 ATM',
      stock: 1,
      brand: 'Audemars Piquet'
    },
    {
      productId: 1,
      price: 14999.0,
      imagePath: 'rolex-daydate-goud.jpg',
      unitsSold: 0,
      name: 'Day-Date',
      description: 'Gouden Rolex Day-Date met groene wijzerplaat. Dagen in het Engels',
      stock: 1,
      brand: 'Rolex'
    },
    {
      productId: 2,
      price: 179.0,
      imagePath: 'seiko-5-staal.jpg',
      unitsSold: 20,
      name: '5 SNKD99K1',
      description: 'Stalen Seiko 5 met datum en dag van de week (Engels en Spaans). Waterdicht tot 50 meter.',
      stock: 10,
      brand: 'Seiko'
    },
    {
      productId: 3,
      price: 179.0,
      imagePath: 'paulhewitt-sailor-zwart.jpg',
      unitsSold: 50,
      name: 'Sailor Black Sunray',
      description: 'Zwart stalen dress-watch van Paul-Hewitt kast is het sailor model met een zwart stalen mesh band. Met gratis armband (zwart nylon touw met zwart stalen anker)',
      stock: 100,
      brand: 'Paul-Hewitt'
    },
    {
      productId: 4,
      price: 24999.0,
      imagePath: 'ap-royaloak15400-staal.jpg',
      unitsSold: 2,
      name: 'Royal Oak Ref 15400',
      description: 'stalen ap royal-oak referentie 15400. Waterbestendigheid: 5 ATM',
      stock: 1,
      brand: 'Audemars Piquet'
    }
  ]
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import zone.js/dist/zone-error;  // Included with Angular CLI.

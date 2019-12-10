var app = angular.module("cartApp", [ "ngRoute" ]);
app.config([ '$locationProvider', function($locationProvider) {
	// $locationProvider.html5Mode(true);
} ]);
function getLocalStorage() {
	var items = JSON.parse(localStorage.getItem("cartItems"));
	return items;
}

function setLocalStorage(item) {
	localStorage.setItem("cartItems", JSON.stringify(item));
}
// Run this at the initialization to fetch all the objects from localStorage
// This enables shopping cart even when the browser is closed in the middle of
// the journey
app.run(function($rootScope) {
	var items = getLocalStorage();
	$rootScope.itemsCount = 0
	if(items){
		$rootScope.itemsInCart = items;
		for(item in $rootScope.itemsInCart) {
			$rootScope.itemsCount += parseInt($rootScope.itemsInCart[item].count)
		}
		console.log("root scope setup is completed")
	}
	else
		$rootScope.itemsInCart = [];
		
})

app.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "pages/main.html",
		controller : "MainController"
	}).when("/orders/:id", {
		templateUrl : "pages/orders.html",
		controller : 'OrderController'
	}).when("/cart", { 
		templateUrl : "pages/review_cart.html", 
		controller : "ReviewCartController"
	}).otherwise({
		redirectTo : "/"
	});
});

app.controller("MainController",function($scope, $http, $rootScope, $window) {
	$scope.addedItems = $rootScope.itemsInCart || [];
	$scope.addItem = function(index, count) {
		console.log(`${index} - ${count}`)
		let _count = parseInt(count) || 0;
		if(_count === 0) {
			alert("Please select a valid number")
			return;
		}
		let _product = $scope.products[index];
		delete _product.createdDate
		delete _product.updatedDate
		let _productsInCart = $scope.addedItems.filter(f => f.id === _product.id);
		if(_productsInCart && _productsInCart[0]) {
			// Product already exists in the cart, so update it
			_productsInCart[0].count += _count
			// This will replace the item at an index matching the id, and still
			// build the entire array
			var res = $scope.addedItems.map(obj => _productsInCart.find(o => o.id === obj.id) || obj);
			$scope.addedItems = []
			$scope.addedItems = res
			$rootScope.itemsCount += _count;
		} else {
			// Add product to cart
			_product.count = _count
			$scope.addedItems.push(_product)	
			$rootScope.itemsCount += _count
		}
		alert(`${_count} ${_product.name}(s) added successfully`)
	}
	// This checks for direct reassignment
	$scope.$watch('addedItems', function() {
// localStorage.setItem("cartItems", JSON.stringify($scope.addedItems));
		setLocalStorage($scope.addItems)
	}, true)
	// This checks for changes at nested/deeper levels
	$scope.$watchCollection('addedItems', function() {
// localStorage.setItem("cartItems", JSON.stringify($scope.addedItems));
		setLocalStorage($scope.addedItems);
	})

	$http.get("/api/products").then((response) => $scope.products = response.data);
});

app.controller("OrderController", function($scope, $http, $rootScope, $routeParams) {
	console.log("OrderControlelr")
	let orderId = $routeParams.id
	$http.get(`/api/orders/${orderId}`).then((response) => {
		let order = response.data
		$scope.orderId = order.orderId;
		$scope.items = order.products;
		
	})
	let storedCart = getLocalStorage();
	localStorage.removeItem("cartItems");
	$rootScope.itemsInCart = []
	$rootScope.itemsCount = 0
});

app.directive("shoppingCart", function() {
	return {
		templateUrl : 'pages/cart.html'
	}
});

app.controller("ReviewCartController", function($scope, $http, $rootScope, $location) {
	$scope.itemsInCart = getLocalStorage();
	$scope.itemsInCart.map(f => f.updated = false)
	let totalPrice = 0
	$scope.itemsInCart.forEach(item => totalPrice += item.price * item.count)
	$scope.totalPrice = totalPrice;
	$scope.onChange= function(index) {
		$scope.itemsInCart[index].updated = true
	}
	$scope.updateItem = function(index) {
// localStorage.setItem("cartItems", JSON.stringify($scope.itemsInCart))
		setLocalStorage($scope.itemsInCart);
		$scope.itemsInCart[index].updated = false;
		let newCount = 0
		$scope.itemsInCart.forEach(f => newCount += f.count)
		$rootScope.itemsCount = newCount
	}
	$scope.removeItem = function(index) {
		console.log(index)
		$scope.itemsInCart.splice(index,1)
		if($scope.itemsInCart) {
// localStorage.setItem("cartItems", JSON.stringify($scope.itemsInCart))
			setLocalStorage($scope.itemsInCart);
			$rootScope.itemsInCart = $scope.itemsInCart
		} else {
			localStorage.removeItem("cartItems")
			$rootScope.itemsInCart = []
		}
		let newCount = 0
		$scope.itemsInCart.forEach(f => newCount += f.count)
		$rootScope.itemsCount = newCount
	}
	$scope.submitOrder = function() {
		$rootScope.email = $scope.userEmail
		$http.post("/api/orders", $scope.itemsInCart).then(function(response) {
			$location.path(`/orders/${response.data.orderId}`)
		})
		
	}
	
})
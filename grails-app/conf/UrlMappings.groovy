class UrlMappings {

	static mappings = {
		"/authenticate"(controller : "billingStackApi") {
			action = [POST : "authenticate", DELETE : "logout"]
		}
		"/merchants"(controller : "merchantsApi"){
			action = [GET : "list", POST : "create"]
		}
		"/merchants/$id"(controller : "merchantsApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant"(controller : "billingStackApi"){
			action = [GET : "info"]
			constraints { merchant(notEqual: 'dbconsole') }
		}
		"/$merchant/payment-gateways"(controller : "paymentGatewaysApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/payment-gateways/$id"(controller : "paymentGatewaysApi"){
			action = [GET : "show", PUT : "update", DELETE : "delete"]
		}
		"/$merchant/payment-gateways/$id/action"(controller : "paymentGatewaysApi"){
			action = [POST : "action"]
		}
		"/$merchant/customers"(controller : "customersApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/customers/$id"(controller : "customersApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant/customers/$customer/credit-cards"(controller : "creditCardsApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/customers/$customer/credit-cards/$id"(controller : "creditCardsApi"){
			action = [DELETE : "delete"]
		}
		"/$merchant/customers/$customer/subscriptions"(controller : "subscriptionsApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/customers/$customer/subscriptions/$id"(controller : "subscriptionsApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant/customers/$customer/subscriptions/$subscription/usage"(controller : "usageApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/customers/$customer/invoices"(controller : "invoicesApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/customers/$customer/invoices/$id"(controller : "invoicesApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant/customers/$customer/transactions"(controller : "transactionsApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/customers/$customer/transactions/$id"(controller : "transactionsApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant/subscriptions"(controller : "subscriptionsApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/invoices"(controller : "invoicesApi"){
			action = [GET : "list"]
		}
		"/$merchant/invoices/$id"(controller : "invoicesApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant/transactions"(controller : "transactionsApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/transactions/$id"(controller : "transactionsApi"){
			action = [GET : "show"]
		}
		"/$merchant/transactions/$id/action"(controller : "transactionsApi"){
			action = [POST : "action"]
		}
		"/$merchant/products"(controller : "productsApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/products/$id"(controller : "productsApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant/plans"(controller : "plansApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/plans/$id"(controller : "plansApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant/plans/$plan/products"(controller : "planProductsApi"){
			action = [GET : "list"]
		}
		"/$merchant/plans/$plan/products/$product"(controller : "planProductsApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant/plans/$plan/products/$product/rules"(controller : "planProductRulesApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/plans/$plan/products/$product/rules/$id"(controller : "planProductRulesApi"){
			action = [DELETE : "delete"]
		}
		"/$merchant/bill"(controller : "billingStackApi"){
			action = [POST : "bill"]
		}
		"/$merchant/customers/$customer/bill"(controller : "billingStackApi"){
			action = [POST : "bill"]
		}
		"/$merchant/customers/$customer/subscriptions/$subscription/bill"(controller : "billingStackApi"){
			action = [POST : "bill"]
		}
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller:"application")
		"500"(view:'/error')
	}
}

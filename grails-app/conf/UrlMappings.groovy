class UrlMappings {

	static mappings = {
		"/authenticate"(controller : "billingStackApi") {
			action = [POST : "authenticate", DELETE : "logout"]
		}
		"/merchants"(controller : "merchantsApi"){
			action = [GET : "list", POST : "create"]
		}
		"/users"(controller : "usersApi") {
			action = [POST : "create", GET : "list"]
		}
		"/roles"(controller : "rolesApi") {
			action = [GET : "list"]
		}
		"/merchants/$id"(controller : "merchantsApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant"(controller : "billingStackApi"){
			action = [GET : "info"]
			constraints { merchant(notEqual: 'dbconsole') }
		}
		"/$merchant/users"(controller : "usersApi"){
			action = [POST : "create", GET : "list"]
		}
		"/$merchant/users/$id"(controller : "usersApi"){
			action = [GET : "show"]
		}
		"/$merchant/users/$user/roles"(controller : "userRolesApi"){
			action = [GET : "list"]
		}
		"/$merchant/users/$user/roles/$id"(controller : "userRolesApi"){
			action = [PUT : "create", DELETE : "delete"]
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
		"/$merchant/customers/$customer/users"(controller : "usersApi"){
			action = [POST : "create", GET : "list"]
		}
		"/$merchant/customers/$customer/users/$id"(controller : "usersApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
		}
		"/$merchant/customers/$customer/users/$user/roles"(controller : "userRolesApi"){
			action = [GET : "list"]
		}
		"/$merchant/customers/$customer/users/$user/roles/$id"(controller : "userRolesApi"){
			action = [PUT : "create", DELETE : "delete"]
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
		"/$merchant/customers/$customer/subscriptions/$subscription/usages"(controller : "usageApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/customers/$customer/usages"(controller : "usageApi"){
			action = [GET : "list", POST : "create"]
		}
		"/$merchant/usages"(controller : "usageApi"){
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
		"/$merchant/subscriptions/$id"(controller : "subscriptionsApi"){
			action = [GET : "show", DELETE : "delete", PUT : "update"]
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

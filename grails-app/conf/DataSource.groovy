dataSource {
	pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
	username = "pcontent"
	password = "menu25serv56frtfz"
}

hibernate {
    cache.use_second_level_cache=false
    cache.use_query_cache=false
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost:3306/pcontent_dev?useUnicode=true&characterEncoding=UTF-8" // "jdbc:hsqldb:mem:testDb" // "jdbc:hsqldb:file:prodDb;shutdown=true"
		}
	}
	test {
		dataSource {
			dbCreate = "create-drop"
			url = "jdbc:mysql://localhost:3306/pcontent_test?useUnicode=true&characterEncoding=UTF-8"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://localhost:3306/pcontent?useUnicode=true&characterEncoding=UTF-8"
		}
	}
}
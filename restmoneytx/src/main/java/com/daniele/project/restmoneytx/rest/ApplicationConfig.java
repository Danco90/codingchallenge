package com.daniele.project.restmoneytx.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
	
//	@Override
//    public Set<Class<?>> getClasses() {
//        final Set<Class<?>> classes = new HashSet<>();
//        classes.add(BankAccountsResource.class);
//        return classes;
//    }
//    @Override
//    public Set<Object> getSingletons() {
//        final Set<Object> singletons = new HashSet<>();
//        singletons.add(new MyProvider());
//        return singletons;
//    }
//
//    @Override
//    public Map<String, Object> getProperties() {
//        final Map<String, Object> properties = new HashMap<>();
//        properties.put("jersey.config.server.provider.packages",
//                       "com.daniele.project.restmoneytx.rest");
//        return properties;
//    }

}


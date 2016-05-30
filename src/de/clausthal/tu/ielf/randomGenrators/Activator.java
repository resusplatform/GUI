package de.clausthal.tu.ielf.randomGenrators;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

//your BundleActivator implementation will probably look something
//like the following
public class Activator implements BundleActivator {
private static Activator instance;

private Bundle bundle;

public void start(BundleContext context) throws Exception {
 instance = this;
 bundle = context.getBundle();
}

public void stop(BundleContext context) throws Exception {
 instance = null;
}

public static Activator getDefault() {
 return instance;
}

public Bundle getBundle() {
 return bundle;
}
}



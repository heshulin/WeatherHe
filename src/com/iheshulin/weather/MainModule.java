package com.iheshulin.weather;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/**
 * Created by HeShulin on 2017/11/24.
 */
@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/",
        "*anno", "com.iheshulin.weather",
        "*tx",
        "*async"})
@IocBean
@Modules(scanPackage=true)
public class MainModule {

}

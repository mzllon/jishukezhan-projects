package com.jishukezhan.core.io.resource;

import com.jishukezhan.core.lang.ArrayUtil;
import com.jishukezhan.core.lang.CollectionUtil;
import com.jishukezhan.core.lang.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源解析
 */
public class ResourceLoader {

    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    private List<String> locationList;

    public ResourceLoader() {
        this.locationList = new ArrayList<>();
    }

    public ResourceLoader(String... locations) {
        this();
        setLocationList(locations);
    }

    public ResourceLoader(List<String> locationList) {
        this();
        setLocationList(locationList);
    }

    public List<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<String> locationList) {
        if (CollectionUtil.isNotEmpty(locationList)) {
            for (String location : locationList) {
                if (StringUtil.hasLength(location)) {
                    this.locationList.add(location);
                }
            }
        }
    }

    public void setLocationList(String... locations) {
        if (ArrayUtil.isNotEmpty(locations)) {
            for (String location : locations) {
                if (StringUtil.hasLength(location)) {
                    this.locationList.add(location);
                }
            }
        }
    }

    public List<Resource> load() {
        Resource resource;
        List<Resource> resourceList = new ArrayList<>(locationList.size() << 1);
        if (CollectionUtil.isNotEmpty(locationList)) {
            for (String location : locationList) {
                if (StringUtil.hasLength(location)) {
                    resource = load(location);
                    if (resource != null) {
                        resourceList.add(resource);
                    }
                }
            }
        }
        return resourceList;
    }

    private Resource load(String location) {
        // AntPathMatcher
        // https://github.com/core-lib/loadkit/blob/master/src/main/java/io/loadkit/AntFilter.java

        Resource resource = null;
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            String realPath = location.substring(CLASSPATH_URL_PREFIX.length());
            if (StringUtil.hasLength(realPath)) {
                resource = new ClasspathResource(realPath);
            }
        } else {
            try {
                URL url = new URL(location);
                resource = new UrlResource(url);
            } catch (MalformedURLException e) {
                //e.printStackTrace();
                resource = new FileResource(location);
            }
        }
        return resource;
    }


}

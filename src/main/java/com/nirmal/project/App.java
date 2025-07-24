package com.nirmal.project;


import com.nirmal.project.controller.CategoryController;
import com.nirmal.project.dao.CategoryDao;
import com.nirmal.project.dao.impl.CategoryDaoImpl;
import com.nirmal.project.db.DBConnectionManager;
import com.nirmal.project.service.CategoryService;
import com.nirmal.project.service.impl.CategoryServiceImpl;
import com.nirmal.project.utils.RestUtils;
import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
    private static MuServer server;
    public static void start() {
        Logger logger = LoggerFactory.getLogger(App.class);
        DBConnectionManager.getConnection();
        CategoryDao categoryDao = new CategoryDaoImpl();
        CategoryService categoryService = new CategoryServiceImpl(categoryDao);
        CategoryController categoryController = new CategoryController(categoryService);

        server = MuServerBuilder.httpServer()
                .withHttpPort(55555)
                .addHandler(Method.GET, "/health", (req, resp, h) -> resp.write("OK"))
                .addHandler(RestUtils.buildHandler(categoryController))
                .start();

        logger.info("Started server at : {}", server.uri());

    }

    public static void stop(){
        DBConnectionManager.closeConnection();
        if(server != null)
            server.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        App.start();
    }
}
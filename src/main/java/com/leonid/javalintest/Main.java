package com.leonid.javalintest;

import com.leonid.javalintest.config.ThymeleafConfig;
import com.leonid.javalintest.controller.ItemController;
import com.leonid.javalintest.domain.User;
import com.leonid.javalintest.dto.Response;
import io.javalin.Javalin;
import io.javalin.core.security.Role;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.crud;
import static org.thymeleaf.templatemode.TemplateMode.HTML;

/**
 * @author Leonid Cheremshantsev
 */
public class Main {

    public static final Role TEST_ROLE_1 = new Role() {

        String name = "1";

        @Override
        public int hashCode() {
            return 1;
        }
    };

    public static final Role TEST_ROLE_2 = new Role() {

        String name = "2";

        @Override
        public int hashCode() {
            return 2;
        }
    };


    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
            config.enableDevLogging();
            JavalinThymeleaf.configure(
                    ThymeleafConfig.templateEngine(
                            ThymeleafConfig.templateResolver(HTML, "/templates/", null)));
        }).start(7000);

        app.config.accessManager((handler, ctx, permittedRoles) -> {
            if (isAllowedForCurrentUser(ctx, permittedRoles)) {
                handler.handle(ctx);
            } else {
                throw new ForbiddenResponse();
            }
        });

        app.get("/", Main::getMainResult, Collections.singleton(TEST_ROLE_1));
        app.get("/users", Main::getUserResult, Collections.singleton(TEST_ROLE_2));
        app.routes(() -> crud("items/:item-id", new ItemController()));
        app.exception(NullPointerException.class, (e, ctx) -> ctx.status(404).json(Response.error("Not found")));
        app.get("/rendered", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("hello", getHelloText(ctx));
            ctx.render("hello.html", model);
        });
    }

    private static boolean isAllowedForCurrentUser(Context ctx, Set<Role> permittedRoles) {
        if (permittedRoles.isEmpty()) {
            return true;
        }
        return getUserRoles(ctx).stream().anyMatch(permittedRoles::contains);
    }

    private static Set<Role> getUserRoles(Context ctx) {
        Set<Role> roles = new HashSet<>();
        roles.add(TEST_ROLE_1);
        return roles;
    }

    private static void getUserResult(Context ctx) {
        ctx.json(new User("Leonid", 25));
    }

    private static void getMainResult(Context ctx) {
        ctx.result(getHelloText(ctx));
    }

    private static String getHelloText(Context ctx) {
        String result = "Hello %s!";

        String name = ctx.queryParam("name");
        if (name == null) {
            name = "World";
        }

        result = String.format(result, name);
        return result;
    }
}

package org.ooptraining;

import lombok.extern.java.Log;
import org.ooptraining.query.QueryCommand;
import org.ooptraining.query.QueryProcessor;
import org.ooptraining.query.QueryResult;
import org.ooptraining.query.argument.ShowQueryCommandArgument;
import org.ooptraining.render.RenderContext;
import org.ooptraining.render.RenderPolicy;
import org.ooptraining.render.Renderer;
import org.ooptraining.render.policy.DefaultRandom;
import org.ooptraining.render.policy.RandomRenderPolicy;
import org.ooptraining.setting.Setting;
import org.ooptraining.setting.SettingContext;
import org.ooptraining.setting.SettingProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Log
public class Main {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        final RenderPolicy policy = RandomRenderPolicy.of(new DefaultRandom());
        run(policy);
    }

    public static void run(final RenderPolicy renderPolicy) {
        final Scanner sc = new Scanner(System.in);

        // TODO: input, output을 injection
        //  반드시 stdin,out말고 file이나 http call 일 수도 있음.
        //  인터페이스 정의한 뒤 삽입
        final SettingProcessor settingProcessor = SettingProcessor.of(sc);
        final SettingContext settingContext = settingProcessor.run(Arrays.asList(
                Setting.NAME,
                Setting.RESULT,
                Setting.MAX_HEIGHT
        ));

        final RenderContext renderContext = RenderContext.builder()
                .intervalWidth(7)
                .maxNameLength(7)
                .build();
        final Renderer renderer = Renderer.of(renderPolicy);
        final String renderResult = renderer.render(settingContext, renderContext);
        System.out.println();
        System.out.println("사다리 결과");
        System.out.println(renderResult);
        System.out.println();

        final QueryProcessor queryProcessor = QueryProcessor.of(sc);
        queryProcessor.run(settingContext);

        // TODO: 마무리 정리 작업 (메시징, 코드 이름, ...)
    }
}

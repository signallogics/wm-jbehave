package org.wmaop.bdd.jbehave;

import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.AnnotatedEmbedderRunner;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wmaop.bdd.jbehave.WmJBehaveSteps;
import org.wmaop.bdd.jbehave.WmFeatureRunner.MyDateConverter;
import org.wmaop.bdd.jbehave.WmFeatureRunner.MyReportBuilder;
import org.wmaop.bdd.jbehave.WmFeatureRunner.MyStoryControls;
import org.wmaop.bdd.jbehave.WmFeatureRunner.MyStoryLoader;

@RunWith(AnnotatedEmbedderRunner.class)
@Configure(storyControls = MyStoryControls.class, storyLoader = MyStoryLoader.class, storyReporterBuilder = MyReportBuilder.class, parameterConverters = { MyDateConverter.class })
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true, ignoreFailureInView = true, verboseFailures = true, storyTimeoutInSecs = 100, threads = 2, metaFilters = "-skip")
@UsingSteps(instances = { WmJBehaveSteps.class })
public class WmFeatureRunner extends InjectableEmbedder {

	@Test
	@Ignore
	public void run() {
		List<String> storyPaths = new StoryFinder().findPaths(new File("src/test/resources").getAbsolutePath(), "**/*.story", "");
		injectedEmbedder().runStoriesAsPaths(storyPaths);
	}

	public static class MyStoryControls extends StoryControls {
		public MyStoryControls() {
			doDryRun(false);
			doSkipScenariosAfterFailure(false);
		}
	}

	public static class MyStoryLoader extends LoadFromClasspath {
		public MyStoryLoader() {
			super(WmFeatureRunner.class.getClassLoader());
		}
	}

	public static class MyReportBuilder extends StoryReporterBuilder {
		public MyReportBuilder() {
			this.withFormats(CONSOLE, TXT, HTML, XML).withDefaultFormats();
		}
	}

	public static class MyRegexPrefixCapturingPatternParser extends RegexPrefixCapturingPatternParser {
		public MyRegexPrefixCapturingPatternParser() {
			super("%");
		}
	}

	public static class MyDateConverter extends DateConverter {
		public MyDateConverter() {
			super(new SimpleDateFormat("yyyy-MM-dd"));
		}
	}

}
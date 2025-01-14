package org.bonitasoft.jenkins.theme.jcasc.materialbonita;

import io.jenkins.plugins.casc.ConfigurationContext;
import io.jenkins.plugins.casc.ConfiguratorRegistry;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.casc.misc.JenkinsConfiguredWithCodeRule;
import io.jenkins.plugins.casc.model.CNode;
import io.jenkins.plugins.thememanager.ThemeManagerFactory;
import io.jenkins.plugins.thememanager.ThemeManagerPageDecorator;
import org.junit.ClassRule;
import org.junit.Test;
import org.bonitasoft.jenkins.theme.MaterialBonitaThemeManagerFactory;

import static io.jenkins.plugins.casc.misc.Util.getUnclassifiedRoot;
import static io.jenkins.plugins.casc.misc.Util.toStringFromYamlFile;
import static io.jenkins.plugins.casc.misc.Util.toYamlString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

public class MaterialBonitaThemeJcascTest {

  @ClassRule
  @ConfiguredWithCode("ConfigurationAsCode.yml")
  public static JenkinsConfiguredWithCodeRule j = new JenkinsConfiguredWithCodeRule();

  @Test
  public void testConfig() {
    ThemeManagerPageDecorator decorator = ThemeManagerPageDecorator.get();

    ThemeManagerFactory theme = decorator.getTheme();
    assertNotNull(theme);

    assertThat(decorator.isDisableUserThemes(), is(true));
    assertThat(theme, instanceOf(MaterialBonitaThemeManagerFactory.class));
  }

  @Test
  public void testExport() throws Exception {
    ConfigurationContext context = new ConfigurationContext(ConfiguratorRegistry.get());
    CNode yourAttribute = getUnclassifiedRoot(context).get("themeManager");

    String exported = toYamlString(yourAttribute);

    String expected = toStringFromYamlFile(this, "ConfigurationAsCodeExport.yml");

    assertThat(exported, is(expected));
  }
}

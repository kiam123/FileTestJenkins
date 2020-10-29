package io.jenkins.plugins.sample;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {
	String filePath;

	@DataBoundConstructor
	public HelloWorldBuilder(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	@Override
	public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener)
			throws InterruptedException, IOException {
		String content = "Files in Java might be tricky, but it is fun enough!";
		getFilePath();

		try {
			listener.getLogger().println(workspace.child(getFilePath()).getParent().getRemote());
			File myObj = new File(workspace.child(getFilePath()).getParent().getRemote() + ".txt");
			if (myObj.createNewFile()) {
				FileWriter myWriter = new FileWriter(workspace.child(getFilePath()).getParent().getRemote() + ".txt");
				myWriter.write(content);
				myWriter.close();
			} else {
				listener.getLogger().println("File already exists.");
			}
		} catch (IOException e) {
			listener.getLogger().println(e);
		}

	}

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "FileTestJenkins";
        }
    }

}

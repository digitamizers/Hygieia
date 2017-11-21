package com.capitalone.dashboard.collector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Bean to hold settings specific to the GitHub collector.
 */
@Component
@ConfigurationProperties(prefix = "github")
public class GitHubSettings {
    private String cron;
    private String host;
    private String key;
    private String userId;
    private String password;
    private String assetId;
    private String orgId;
    private String repoUrl;
    private String branch;
	@Value("${github.firstRunHistoryDays:14}")
    private int firstRunHistoryDays;
    private List<String> notBuiltCommits;
	@Value("${github.errorThreshold:2}")
    private int errorThreshold;
	@Value("${github.rateLimitThreshold:10}")
	private int rateLimitThreshold;
	private String personalAccessToken;


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
    public int getFirstRunHistoryDays() {
		return firstRunHistoryDays;
	}

	public void setFirstRunHistoryDays(int firstRunHistoryDays) {
		this.firstRunHistoryDays = firstRunHistoryDays;
	}

    public List<String> getNotBuiltCommits() {
        return notBuiltCommits;
    }

    public void setNotBuiltCommits(List<String> notBuiltCommits) {
        this.notBuiltCommits = notBuiltCommits;
    }

	public int getErrorThreshold() {
		return errorThreshold;
	}

	public void setErrorThreshold(int errorThreshold) {
		this.errorThreshold = errorThreshold;
	}

	public int getRateLimitThreshold() {
		return rateLimitThreshold;
	}

	public void setRateLimitThreshold(int rateLimitThreshold) {
		this.rateLimitThreshold = rateLimitThreshold;
	}

	public String getPersonalAccessToken() {
		return personalAccessToken;
	}

	public void setPersonalAccessToken(String personalAccessToken) {
		this.personalAccessToken = personalAccessToken;
	}
	
	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

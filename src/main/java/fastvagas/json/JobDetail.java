package fastvagas.json;

import fastvagas.data.entity.PortalJob;

import java.time.LocalDateTime;

public class JobDetail {

    private Long portal_job_id;
    private String name;
    private String company_name;
    private String job_type;
    private String description;
    private String published_at;
    private String url;
    private Long portal_id;
    private String portal_name;
    private LocalDateTime created_at;

    public JobDetail(PortalJob portalJob) {
        setPortal_job_id(portalJob.getId());
        setName(portalJob.getJobTitle());
        setCompany_name(portalJob.getCompanyName());
        setJob_type(portalJob.getJobType());
        setDescription(portalJob.getJobDescription());
        setPublished_at(portalJob.getPublishedAt());
        setUrl(portalJob.getJobUri());
        setPortal_id(portalJob.getPortalId());
        setCreated_at(portalJob.getCreatedAt());
    }

    public Long getPortal_job_id() {
        return portal_job_id;
    }

    public void setPortal_job_id(Long portal_job_id) {
        this.portal_job_id = portal_job_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPortal_id() {
        return portal_id;
    }

    public void setPortal_id(Long portal_id) {
        this.portal_id = portal_id;
    }

    public String getPortal_name() {
        return portal_name;
    }

    public void setPortal_name(String portal_name) {
        this.portal_name = portal_name;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}

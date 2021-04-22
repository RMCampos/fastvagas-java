package fastvagas.service;

import fastvagas.dal.entity.*;
import fastvagas.dal.service.CityService;
import fastvagas.dal.service.PortalJobService;
import fastvagas.dal.service.PortalService;
import fastvagas.dal.service.StateService;
import fastvagas.exception.EntityNotFoundException;
import fastvagas.json.IndexJson;
import fastvagas.json.JobDetail;
import fastvagas.json.JobPagination;
import fastvagas.util.DateUtil;
import fastvagas.util.ObjectUtil;
import fastvagas.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IndexService {

    @Autowired
    CityService cityService;

    @Autowired
    StateService stateService;

    @Autowired
    PortalJobService portalJobService;

    @Autowired
    PortalService portalService;

    public IndexJson getAllJobs(User user) {
        City city = cityService.findById(user.getCity_id());
        if (city == null) {
            throw new EntityNotFoundException(City.class, "city_id", String.valueOf(user.getCity_id()));
        }

        State state = stateService.findById(city.getState_id());
        if (state == null) {
            throw new EntityNotFoundException(State.class, "state_id", String.valueOf(city.getState_id()));
        }

        final Date today = new Date();
        final Integer month = DateUtil.getMonthFromDate(today);
        final Integer year = DateUtil.getYearFromDate(today);
        final Date firstDayOfMonth = DateUtil.createDate(1, month, year);
        final Date weekDate = new Date(); // FIXME

        int weekJobs = 0;
        int todayJobs = 0;

        List<PortalJob> cityJobs = portalJobService.findAllByCityIdPublishedRange(
                city.getCity_id(),
                firstDayOfMonth
        );

        for (PortalJob portalJob : cityJobs) {
            /* This week jobs */
            if (DateUtil.isGreater(weekDate, portalJob.getPublished_at())) {
                weekJobs += 1;
            }

            /* Today jobs */
            if (DateUtil.equalsIgnoringHours(portalJob.getPublished_at(), today)) {
                todayJobs += 1;
            }
        }

        IndexJson indexJson = new IndexJson();
        indexJson.setCityId(city.getCity_id());
        indexJson.setCityName(city.getName());
        indexJson.setStateName(state.getSigla_uf());
        indexJson.setMonthJobs(cityJobs.size());
        indexJson.setWeekJobs(weekJobs);
        indexJson.setTodayJobs(todayJobs);
        indexJson.setUserJobPagination(createJobPagination(null, null, null));
        indexJson.setLastJobPagination(getLastJobs(city.getCity_id(), null));
        indexJson.setTopJobPagination(createJobPagination(null, null, null));

        return indexJson;
    }

    public JobPagination getUserJobs(User user, Integer page) {
        return new JobPagination();
    }

    public JobPagination getLastJobs(Long city_id, Integer page) {
        if (!ObjectUtil.hasValue(page)) {
            page = 1;
        }

        // Map para agilizar
        Map<Long, String> portalNameMap = new HashMap<>();
        List<Portal> portals = portalService.findAllByCityId(city_id);
        portals.forEach(portal -> portalNameMap.put(portal.getPortal_id(), portal.getName()));

        List<JobDetail> jobList = new ArrayList<>();

        List<PortalJob> portalJobsTmp = portalJobService.findAllLastByCityIdPage(city_id, page);
        portalJobsTmp.forEach((job) -> {
            JobDetail jobDetail = new JobDetail(job);
            jobDetail.setPortal_name(portalNameMap.get(job.getPortal_id()));
            jobList.add(jobDetail);
        });

        long count = portalJobService.findAllLastByCityId(city_id).size();

        // sort jobList list

        return createJobPagination(jobList, page, count);
    }

    public JobPagination getTopJobs(Integer page) {
        return new JobPagination();
    }

    private JobPagination createJobPagination(List<JobDetail> jobList, Integer page, Long count) {
        if (!ObjectUtil.hasValue(jobList, page, count)) {
            return new JobPagination();
        }

        PaginationUtil util = new PaginationUtil(count, page);

        JobPagination pagination = new JobPagination();
        pagination.setCurrentPage(page);
        pagination.setPages(util.getPages());
        pagination.setHasNextPage(util.getHasNextPage());
        pagination.setHasPreviousPage(util.getHasPreviousPage());
        pagination.setJobList(jobList);

        return pagination;
    }
}

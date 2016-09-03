package com.dyrs.smjj.itracker.controller;

import com.dyrs.smjj.itracker.control.DepartmentDao;
import com.dyrs.smjj.itracker.control.IssueApplication;
import com.dyrs.smjj.itracker.control.IssueDao;
import com.dyrs.smjj.itracker.control.User_DepDao;
import com.dyrs.smjj.itracker.entity.Department;
import com.dyrs.smjj.itracker.entity.Issue;
import com.dyrs.smjj.itracker.entity.StatusEnum;
import com.dyrs.smjj.itracker.entity.User_Department;
import com.dyrs.smjj.itracker.filter.LoginBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Model
public class IssueService {
    @Inject
    private FacesContext facesContext;

    @Inject
    private IssueDao issueDao;

    @Inject
    private DepartmentDao departmentDao;

    @Inject
    private User_DepDao user_depDao;

    @Inject
    private IssueApplication application;

    @Inject
    private LoginBean loginBean;

    private List<Issue> resolveIssues;

    @Inject
    private Event<Issue> issueEventSrc;

    private long currentIssueId;
    private String comment;
    private Issue currentIssue;
    private int unResolvedCount;

    @Produces
    @Named
    private Issue newIssue;

    @Produces
    @Named
    private Department newDepartment;

    @Produces
    @Named
    private List<String> selectedDeps;

    public void setSelectedDeps(List<String> selectedDeps) {
        this.selectedDeps = selectedDeps;
    }

    public List<String> getSelectedDeps() {
        List<User_Department> uds = user_depDao.findAll().stream().filter(ud -> ud.getUserId().equals(String.valueOf(loginBean.getUser().getId()))).collect(Collectors.toList());
        List<Department> sds = departmentDao.findAll().stream().filter(department -> uds.stream().anyMatch(u -> u.getDepartmentName().equals(department.getName()))).collect(Collectors.toList());
        return sds.stream().map(Department::getName).collect(Collectors.toList());
    }

    public List<String> getUnSelectDeps() {
        List<User_Department> uds = user_depDao.findAll().stream().filter(ud -> ud.getUserId().equals(String.valueOf(loginBean.getUser().getId()))).collect(Collectors.toList());
        List<Department> sds = departmentDao.findAll().stream().filter(department -> department.isUsed() == false || uds.stream().anyMatch(u -> u.getDepartmentName().equals(department.getName()))).collect(Collectors.toList());
        return sds.stream().map(Department::getName).collect(Collectors.toList());
    }

    public void setUnSelectDeps(List<String> unSelectDeps) {
        this.unSelectDeps = unSelectDeps;
    }

    @Produces
    @Named
    private List<String> unSelectDeps;

    @PostConstruct
    public void initNewIssue() {
        newIssue = new Issue();
        newIssue.setStatus(StatusEnum.Waiting);
        newDepartment = new Department();
    }

    public String onLoad() {
        String username = newIssue.getUserName();
        String department = newIssue.getDepartment();
        if (StringUtils.isNotEmpty(username) && StringUtils.isNoneEmpty(department)) {
            loginBean.setDepartment(newIssue.getDepartment());
            loginBean.setUsername(newIssue.getUserName());
            loginBean.setCategory(newIssue.getCategory());
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("login", null);
        }
        newIssue.setCategory("请选择");
        return "";
    }

    public String getIssues() throws Exception {
        Map<String, Long> map = application.getWaitingIssue();
        String key = loginBean.getCategory();
        if (map.containsKey(key)) {
            long id = map.get(key);
            return issueDao.find(id).getContent();
        }
        return null;
    }

    public String getMessage() {
        Map<String, Long> map = application.getWaitingIssue();
        String key = loginBean.getCategory();
        if (map.containsKey(key)) {
            long id = map.get(key);
            map.remove(key);
            String content = issueDao.find(id).getContent();
            String plain = Jsoup.parse(content).text();
            return plain;
        }

        return null;
    }

    public String getOriginString(String html_string) {
        String plain = Jsoup.parse(html_string).text();
        return plain;
    }

    public List<String> getFilterCategory() {
        List<String> csList = new ArrayList<>(Arrays.asList("DIM", "HDS", "OCRM", "2020"));
        csList.remove(loginBean.getCategory());
        return csList;
    }

    public String getEngineer(String category) {
        return application.getOnLineSolver().get(category);
    }

    public void export2Csv() throws Exception {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet title");
        int rowIndex = 0;
        resolveIssues = issueDao.getResolveIssues(loginBean.getCategory());
        Row row = sheet.createRow(rowIndex++);
        int columnIndex = 0;

        row.createCell(columnIndex++).setCellValue("单号");
        row.createCell(columnIndex++).setCellValue("分类");
        row.createCell(columnIndex++).setCellValue("问题描述");

        row.createCell(columnIndex++).setCellValue("客户");
        row.createCell(columnIndex++).setCellValue("提报人");
        row.createCell(columnIndex++).setCellValue("提报时间");

        row.createCell(columnIndex++).setCellValue("处理人");
        row.createCell(columnIndex++).setCellValue("处理时间");
        row.createCell(columnIndex++).setCellValue("处理意见");

        for (Issue item : resolveIssues) {
            row = sheet.createRow(rowIndex++);
            columnIndex = 0;

            row.createCell(columnIndex++).setCellValue(item.getOrderNo());
            row.createCell(columnIndex++).setCellValue(item.getCategory());
            row.createCell(columnIndex++).setCellValue(getOriginString(item.getContent()));

            row.createCell(columnIndex++).setCellValue(item.getCustomer());
            row.createCell(columnIndex++).setCellValue(item.getUserName());
            row.createCell(columnIndex++)
                    .setCellValue(item.getOrderDate() != null ? item.getOrderDate().toString() : "");

            row.createCell(columnIndex++).setCellValue(item.getSolvedBy() != null ? item.getSolvedBy().getName() : "");
            row.createCell(columnIndex++).setCellValue(item.getSolvedOn() != null ? item.getSolvedOn().toString() : "");
            row.createCell(columnIndex++).setCellValue(item.getSolvedComment());

        }
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=Issues.xls");
        workbook.write(externalContext.getResponseOutputStream());
        context.responseComplete();
    }

    public void addNewDepartment() {
        departmentDao.persist(newDepartment);
        newDepartment = new Department();
    }

    public void addNewIssus() throws Exception {
        try {
            if (newIssue.getCategory().equals("请选择")) {
                newIssue.setCategory(null);
            }
            if (newIssue.getId() > 0) {
                newIssue.setStatus(StatusEnum.Waiting);
                DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String sdt = df.format(new Date(System.currentTimeMillis()));
                newIssue.setOrderNo(newIssue.getCategory() + sdt);
                newIssue.setOrderDate(new java.util.Date());
                issueDao.edit(newIssue);
            } else {
                createIssue(newIssue);
            }
            // Init UserSession
            loginBean.setMobile(newIssue.getMobile());
            application.getWaitingIssue().put(newIssue.getCategory(), newIssue.getId());
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "OK!", "问题提交成功");
            facesContext.addMessage(null, m);
            initNewIssue();
        } catch (Exception e) {
            final String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error while saving data");
            facesContext.addMessage(null, m);
        }
    }

    public String solveIssue(long id) {
        Issue issue = issueDao.find(id);
        if (issue.getStatus() == StatusEnum.Waiting) {
            issue.setStatus(StatusEnum.Processing);
            issue.setSolvedBy(loginBean.getUser());
            issueDao.edit(issue);
        }

        return "success";
    }

    public String reOpenIssue(long id) {
        Issue issue = issueDao.find(id);
        if (issue.getStatus() == StatusEnum.Completed) {
            issue.setStatus(StatusEnum.Waiting);
            issue.setSolvedBy(loginBean.getUser());
        }

        issueDao.edit(issue);
        return "success";
    }

    public void reEdit(long id) {
        newIssue = issueDao.find(id);
    }

    public String completeIssue(long id, String comment) throws Exception {
        Issue issue = issueDao.find(id);
        if (issue.getStatus() == StatusEnum.Processing) {
            issue.setStatus(StatusEnum.Completed);
            issue.setSolvedBy(loginBean.getUser());
            issue.setSolvedOn(new java.util.Date());
            issue.setSolvedComment(comment);
        }

        issueDao.edit(issue);

        return "success";
    }

    public String returnIssue(long id, String comment) throws Exception {
        Issue issue = issueDao.find(id);
        if (issue.getStatus() == StatusEnum.Waiting) {
            issue.setStatus(StatusEnum.Refused);
            issue.setSolvedBy(loginBean.getUser());
            issue.setSolvedOn(new java.util.Date());
            issue.setSolvedComment(comment);
        }

        issueDao.edit(issue);

        return "success";
    }

    public void transferIssue(long id, String category, String comment) {
        Issue issue = issueDao.find(id);
        if (issue.getStatus() == StatusEnum.Waiting) {
            issue.setCategory(category);
            issue.setTransferBy(loginBean.getUsername());
            issue.setTransferOn(new java.util.Date());
            issue.setTransferComment(comment);
        }

        issueDao.edit(issue);
    }

    public String getStatus(StatusEnum status) {
        switch (status) {
            case Waiting:
                return "等待处理";
            case Processing:
                return "处理中";
            case Completed:
                return "处理完成";
            case Refused:
                return "退回";

            default:
                return "未知";
        }
    }

    public StatusEnum getOriStatus(String status) {
        switch (status) {
            case "等待处理":
                return StatusEnum.Waiting;
            case "处理中":
                return StatusEnum.Processing;
            case "处理完成":
                return StatusEnum.Completed;

            default:
                return StatusEnum.Waiting;
        }
    }

    public String[] getSubCategories() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String category = ec.getRequestParameterMap().get("reg:category");
        String[] subcategorys = new String[]{"请选择子类别"};
        if (category != null) {
            switch (category) {
                case "DIM":
                    subcategorys = (String[]) ArrayUtils.addAll(subcategorys,
                            new String[]{"渲染", "报价领料", "建模", "变更", "施工图", "木作"});
                    break;
                default:
                    break;
            }
        }

        return subcategorys;
    }

    public void createIssue(Issue issue) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String sdt = df.format(new Date(System.currentTimeMillis()));
        issue.setOrderNo(issue.getCategory() + sdt);
        issue.setOrderDate(new java.util.Date());
        issue.setIssueType("");
        String dep = issue.getDepartment();

        if (issue.getCategory().equalsIgnoreCase("HDS") && getSelectedDeps().stream().anyMatch(sd -> sd.equals(dep))) {
            issue.setOwnerId(loginBean.getUser().getId());
            issue.setHolding(true);
        }
        issueDao.persist(issue);
        issueEventSrc.fire(issue);
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "提交问题  失败 . 查看服务器错误日志，获得更多帮助";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

    public long getCurrentIssueId() {
        return currentIssueId;
    }

    public void setCurrentIssueId(long currentIssueId) {
        this.currentIssueId = currentIssueId;
    }

    public Issue getCurrentIssue() {
        if (currentIssueId > 0) {
            return issueDao.find(currentIssueId);
        }
        return newIssue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUnResolvedCount() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String category = ec.getRequestParameterMap().get("reg:category");
        List<Issue> issues = issueDao.getResolveIssues(category);
        if (issues == null) {
            return 999;
        }
        unResolvedCount = (int) issues.stream()
                .filter(r -> r.getStatus() == StatusEnum.Waiting || r.getStatus() == StatusEnum.Processing).count();
        return unResolvedCount;
    }

    public void setUnResolvedCount(int unResolvedCount) {
        this.unResolvedCount = unResolvedCount;
    }

    public String getCurrentUser() {
        return application.getCurrentUser().get("HDS");
    }

    public void updateUserDep() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String pick = ec.getRequestParameterMap().get("form_pick:pick");

        if (StringUtils.isNotBlank(pick)) {
            for (String name : StringUtils.split(pick, ',')) {
                Department dep = departmentDao.findAll().stream().filter(department -> department.getName().equals(name)).findFirst().orElse(null);
                if (dep != null) {
                    dep.setUsed(true);

                    User_Department ud = new User_Department();
                    ud.setDepartmentId(String.valueOf(dep.getId()));
                    ud.setDepartmentName(dep.getName());
                    ud.setUserId(String.valueOf(loginBean.getUser().getId()));
                    ud.setUserName(loginBean.getUsername());


                    user_depDao.persist(ud);
                    departmentDao.edit(dep);
                }
            }
        }
    }
}

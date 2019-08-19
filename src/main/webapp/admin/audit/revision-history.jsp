<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/_include/Head.jsp"%>
<title>修改历史</title>
<style type="text/css">
</style>
</head>
<body>
<div class="rb-wrapper rb-fixed-sidebar rb-collapsible-sidebar rb-collapsible-sidebar-hide-logo rb-color-header">
<jsp:include page="/_include/NavTop.jsp">
    <jsp:param value="修改历史" name="pageTitle"/>
</jsp:include>
<jsp:include page="/_include/NavLeftAdmin.jsp">
    <jsp:param value="revision-history" name="activeNav"/>
</jsp:include>
<div class="rb-content">
    <div class="main-content container-fluid">
        <div class="card card-table">
            <div class="card-body">
                <div class="dataTables_wrapper container-fluid">
                    <div class="row rb-datatable-header">
                        <div class="col-12 col-md-6">
                            <div class="dataTables_filter">
                                <div class="input-group input-search" data-qfields="&user,user.loginName,user.email,ipAddr">
                                    <input class="form-control" type="text" placeholder="查询${entityLabel}" maxlength="40">
                                    <span class="input-group-btn"><button class="btn btn-secondary" type="button"><i class="icon zmdi zmdi-search"></i></button></span>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <div class="dataTables_oper">
                                <div class="btn-group btn-space">
                                    <button class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown">更多 <i class="icon zmdi zmdi-more-vert"></i></button>
                                    <div class="dropdown-menu dropdown-menu-right">
                                        <a class="dropdown-item J_columns"><i class="icon zmdi zmdi-code-setting"></i> 列显示</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="react-list" class="rb-loading rb-loading-active data-list">
                        <%@ include file="/_include/spinner.jsp"%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<%@ include file="/_include/Foot.jsp"%>
<script type="text/babel">
</script>
</body>
</html>

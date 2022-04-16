package cn.gpnusz.ucloudteach.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SnapshotExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SnapshotExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterionForJDBCDate("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterionForJDBCDate("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterionForJDBCDate("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterionForJDBCDate("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCourseCountIsNull() {
            addCriterion("course_count is null");
            return (Criteria) this;
        }

        public Criteria andCourseCountIsNotNull() {
            addCriterion("course_count is not null");
            return (Criteria) this;
        }

        public Criteria andCourseCountEqualTo(Integer value) {
            addCriterion("course_count =", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountNotEqualTo(Integer value) {
            addCriterion("course_count <>", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountGreaterThan(Integer value) {
            addCriterion("course_count >", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_count >=", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountLessThan(Integer value) {
            addCriterion("course_count <", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountLessThanOrEqualTo(Integer value) {
            addCriterion("course_count <=", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountIn(List<Integer> values) {
            addCriterion("course_count in", values, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountNotIn(List<Integer> values) {
            addCriterion("course_count not in", values, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountBetween(Integer value1, Integer value2) {
            addCriterion("course_count between", value1, value2, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountNotBetween(Integer value1, Integer value2) {
            addCriterion("course_count not between", value1, value2, "courseCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountIsNull() {
            addCriterion("student_count is null");
            return (Criteria) this;
        }

        public Criteria andStudentCountIsNotNull() {
            addCriterion("student_count is not null");
            return (Criteria) this;
        }

        public Criteria andStudentCountEqualTo(Integer value) {
            addCriterion("student_count =", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountNotEqualTo(Integer value) {
            addCriterion("student_count <>", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountGreaterThan(Integer value) {
            addCriterion("student_count >", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("student_count >=", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountLessThan(Integer value) {
            addCriterion("student_count <", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountLessThanOrEqualTo(Integer value) {
            addCriterion("student_count <=", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountIn(List<Integer> values) {
            addCriterion("student_count in", values, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountNotIn(List<Integer> values) {
            addCriterion("student_count not in", values, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountBetween(Integer value1, Integer value2) {
            addCriterion("student_count between", value1, value2, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("student_count not between", value1, value2, "studentCount");
            return (Criteria) this;
        }

        public Criteria andPaperCountIsNull() {
            addCriterion("paper_count is null");
            return (Criteria) this;
        }

        public Criteria andPaperCountIsNotNull() {
            addCriterion("paper_count is not null");
            return (Criteria) this;
        }

        public Criteria andPaperCountEqualTo(Integer value) {
            addCriterion("paper_count =", value, "paperCount");
            return (Criteria) this;
        }

        public Criteria andPaperCountNotEqualTo(Integer value) {
            addCriterion("paper_count <>", value, "paperCount");
            return (Criteria) this;
        }

        public Criteria andPaperCountGreaterThan(Integer value) {
            addCriterion("paper_count >", value, "paperCount");
            return (Criteria) this;
        }

        public Criteria andPaperCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("paper_count >=", value, "paperCount");
            return (Criteria) this;
        }

        public Criteria andPaperCountLessThan(Integer value) {
            addCriterion("paper_count <", value, "paperCount");
            return (Criteria) this;
        }

        public Criteria andPaperCountLessThanOrEqualTo(Integer value) {
            addCriterion("paper_count <=", value, "paperCount");
            return (Criteria) this;
        }

        public Criteria andPaperCountIn(List<Integer> values) {
            addCriterion("paper_count in", values, "paperCount");
            return (Criteria) this;
        }

        public Criteria andPaperCountNotIn(List<Integer> values) {
            addCriterion("paper_count not in", values, "paperCount");
            return (Criteria) this;
        }

        public Criteria andPaperCountBetween(Integer value1, Integer value2) {
            addCriterion("paper_count between", value1, value2, "paperCount");
            return (Criteria) this;
        }

        public Criteria andPaperCountNotBetween(Integer value1, Integer value2) {
            addCriterion("paper_count not between", value1, value2, "paperCount");
            return (Criteria) this;
        }

        public Criteria andQuestionCountIsNull() {
            addCriterion("question_count is null");
            return (Criteria) this;
        }

        public Criteria andQuestionCountIsNotNull() {
            addCriterion("question_count is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionCountEqualTo(Integer value) {
            addCriterion("question_count =", value, "questionCount");
            return (Criteria) this;
        }

        public Criteria andQuestionCountNotEqualTo(Integer value) {
            addCriterion("question_count <>", value, "questionCount");
            return (Criteria) this;
        }

        public Criteria andQuestionCountGreaterThan(Integer value) {
            addCriterion("question_count >", value, "questionCount");
            return (Criteria) this;
        }

        public Criteria andQuestionCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("question_count >=", value, "questionCount");
            return (Criteria) this;
        }

        public Criteria andQuestionCountLessThan(Integer value) {
            addCriterion("question_count <", value, "questionCount");
            return (Criteria) this;
        }

        public Criteria andQuestionCountLessThanOrEqualTo(Integer value) {
            addCriterion("question_count <=", value, "questionCount");
            return (Criteria) this;
        }

        public Criteria andQuestionCountIn(List<Integer> values) {
            addCriterion("question_count in", values, "questionCount");
            return (Criteria) this;
        }

        public Criteria andQuestionCountNotIn(List<Integer> values) {
            addCriterion("question_count not in", values, "questionCount");
            return (Criteria) this;
        }

        public Criteria andQuestionCountBetween(Integer value1, Integer value2) {
            addCriterion("question_count between", value1, value2, "questionCount");
            return (Criteria) this;
        }

        public Criteria andQuestionCountNotBetween(Integer value1, Integer value2) {
            addCriterion("question_count not between", value1, value2, "questionCount");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
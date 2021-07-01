package com.pblogteam.pblog.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ArticleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ArticleExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdIsNull() {
            addCriterion("article_type_id is null");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdIsNotNull() {
            addCriterion("article_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdEqualTo(Integer value) {
            addCriterion("article_type_id =", value, "articleTypeId");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdNotEqualTo(Integer value) {
            addCriterion("article_type_id <>", value, "articleTypeId");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdGreaterThan(Integer value) {
            addCriterion("article_type_id >", value, "articleTypeId");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("article_type_id >=", value, "articleTypeId");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdLessThan(Integer value) {
            addCriterion("article_type_id <", value, "articleTypeId");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("article_type_id <=", value, "articleTypeId");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdIn(List<Integer> values) {
            addCriterion("article_type_id in", values, "articleTypeId");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdNotIn(List<Integer> values) {
            addCriterion("article_type_id not in", values, "articleTypeId");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("article_type_id between", value1, value2, "articleTypeId");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("article_type_id not between", value1, value2, "articleTypeId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Date value) {
            addCriterionForJDBCDate("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterionForJDBCDate("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterionForJDBCDate("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Date> values) {
            addCriterionForJDBCDate("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date not between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andPublishedIsNull() {
            addCriterion("published is null");
            return (Criteria) this;
        }

        public Criteria andPublishedIsNotNull() {
            addCriterion("published is not null");
            return (Criteria) this;
        }

        public Criteria andPublishedEqualTo(Byte value) {
            addCriterion("published =", value, "published");
            return (Criteria) this;
        }

        public Criteria andPublishedNotEqualTo(Byte value) {
            addCriterion("published <>", value, "published");
            return (Criteria) this;
        }

        public Criteria andPublishedGreaterThan(Byte value) {
            addCriterion("published >", value, "published");
            return (Criteria) this;
        }

        public Criteria andPublishedGreaterThanOrEqualTo(Byte value) {
            addCriterion("published >=", value, "published");
            return (Criteria) this;
        }

        public Criteria andPublishedLessThan(Byte value) {
            addCriterion("published <", value, "published");
            return (Criteria) this;
        }

        public Criteria andPublishedLessThanOrEqualTo(Byte value) {
            addCriterion("published <=", value, "published");
            return (Criteria) this;
        }

        public Criteria andPublishedIn(List<Byte> values) {
            addCriterion("published in", values, "published");
            return (Criteria) this;
        }

        public Criteria andPublishedNotIn(List<Byte> values) {
            addCriterion("published not in", values, "published");
            return (Criteria) this;
        }

        public Criteria andPublishedBetween(Byte value1, Byte value2) {
            addCriterion("published between", value1, value2, "published");
            return (Criteria) this;
        }

        public Criteria andPublishedNotBetween(Byte value1, Byte value2) {
            addCriterion("published not between", value1, value2, "published");
            return (Criteria) this;
        }

        public Criteria andCollectionCountIsNull() {
            addCriterion("collection_count is null");
            return (Criteria) this;
        }

        public Criteria andCollectionCountIsNotNull() {
            addCriterion("collection_count is not null");
            return (Criteria) this;
        }

        public Criteria andCollectionCountEqualTo(Integer value) {
            addCriterion("collection_count =", value, "collectionCount");
            return (Criteria) this;
        }

        public Criteria andCollectionCountNotEqualTo(Integer value) {
            addCriterion("collection_count <>", value, "collectionCount");
            return (Criteria) this;
        }

        public Criteria andCollectionCountGreaterThan(Integer value) {
            addCriterion("collection_count >", value, "collectionCount");
            return (Criteria) this;
        }

        public Criteria andCollectionCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("collection_count >=", value, "collectionCount");
            return (Criteria) this;
        }

        public Criteria andCollectionCountLessThan(Integer value) {
            addCriterion("collection_count <", value, "collectionCount");
            return (Criteria) this;
        }

        public Criteria andCollectionCountLessThanOrEqualTo(Integer value) {
            addCriterion("collection_count <=", value, "collectionCount");
            return (Criteria) this;
        }

        public Criteria andCollectionCountIn(List<Integer> values) {
            addCriterion("collection_count in", values, "collectionCount");
            return (Criteria) this;
        }

        public Criteria andCollectionCountNotIn(List<Integer> values) {
            addCriterion("collection_count not in", values, "collectionCount");
            return (Criteria) this;
        }

        public Criteria andCollectionCountBetween(Integer value1, Integer value2) {
            addCriterion("collection_count between", value1, value2, "collectionCount");
            return (Criteria) this;
        }

        public Criteria andCollectionCountNotBetween(Integer value1, Integer value2) {
            addCriterion("collection_count not between", value1, value2, "collectionCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountIsNull() {
            addCriterion("comment_count is null");
            return (Criteria) this;
        }

        public Criteria andCommentCountIsNotNull() {
            addCriterion("comment_count is not null");
            return (Criteria) this;
        }

        public Criteria andCommentCountEqualTo(Integer value) {
            addCriterion("comment_count =", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotEqualTo(Integer value) {
            addCriterion("comment_count <>", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountGreaterThan(Integer value) {
            addCriterion("comment_count >", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_count >=", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountLessThan(Integer value) {
            addCriterion("comment_count <", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountLessThanOrEqualTo(Integer value) {
            addCriterion("comment_count <=", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountIn(List<Integer> values) {
            addCriterion("comment_count in", values, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotIn(List<Integer> values) {
            addCriterion("comment_count not in", values, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountBetween(Integer value1, Integer value2) {
            addCriterion("comment_count between", value1, value2, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_count not between", value1, value2, "commentCount");
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
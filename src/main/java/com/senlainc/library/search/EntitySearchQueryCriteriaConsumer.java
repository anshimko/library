package com.senlainc.library.search;

import java.util.function.Consumer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EntitySearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {

	private Predicate predicate;
	private CriteriaBuilder builder;
	private Root<?> root;

	public EntitySearchQueryCriteriaConsumer(Predicate predicate, CriteriaBuilder builder, Root<?> root) {
		this.predicate = predicate;
		this.builder = builder;
		this.root = root;
	}

	public Predicate getPredicate() {
		return predicate;
	}

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public CriteriaBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(CriteriaBuilder builder) {
		this.builder = builder;
	}

	public Root<?> getRoot() {
		return root;
	}

	public void setRoot(Root<?> root) {
		this.root = root;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((builder == null) ? 0 : builder.hashCode());
		result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntitySearchQueryCriteriaConsumer other = (EntitySearchQueryCriteriaConsumer) obj;
		if (builder == null) {
			if (other.builder != null)
				return false;
		} else if (!builder.equals(other.builder))
			return false;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}

	@Override
	public void accept(SearchCriteria param) {

		if (param.getOperation().equalsIgnoreCase(">")) {
			predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(param.getKey()), param.getValue().toString()));
		} else if (param.getOperation().equalsIgnoreCase("<")) {
			predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get(param.getKey()), param.getValue().toString()));
		} else if (param.getOperation().equalsIgnoreCase(":")) {
			if (root.get(param.getKey()).getJavaType() == String.class) {
				predicate = builder.and(predicate, builder.like(root.get(param.getKey()), "%" + param.getValue() + "%"));
			} else {
				predicate = builder.and(predicate, builder.equal(root.get(param.getKey()), param.getValue()));
			}
		}
	}

}
package com.newDemom.BudgetApplication.Mapper;

public interface Mapper<A, B>{

    B MapTo(A a);

    A MapFrom(B b);
}

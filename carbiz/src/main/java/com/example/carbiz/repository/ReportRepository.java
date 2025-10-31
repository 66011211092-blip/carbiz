package com.example.carbiz.repository;

import com.example.carbiz.entity.Sale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends Repository<Sale, Long> {

    /* -------- OVERVIEW เดิม -------- */
    @Query("""
        select count(s), coalesce(sum(s.finalPrice),0),
               coalesce(sum(s.finalPrice - coalesce(c.purchasePrice,0)),0)
        from Sale s
        join s.car c
        where (:start is null or s.saleDate >= :start)
          and (:end   is null or s.saleDate <= :end)
    """)
    Object[] overview(@Param("start") LocalDate start,
                      @Param("end")   LocalDate end);

    /* -------- DAILY เดิม -------- */
    @Query("""
        select function('date', s.saleDate) as d,
               count(s) as deals,
               coalesce(sum(s.finalPrice),0) as revenue,
               coalesce(sum(s.finalPrice - coalesce(c.purchasePrice,0)),0) as profit
        from Sale s
        join s.car c
        where (:start is null or s.saleDate >= :start)
          and (:end   is null or s.saleDate <= :end)
        group by function('date', s.saleDate)
        order by d asc
    """)
    List<Object[]> daily(@Param("start") LocalDate start,
                         @Param("end")   LocalDate end);

    /* -------- MONTHLY เดิม -------- */
    @Query("""
        select function('year', s.saleDate)  as y,
               function('month', s.saleDate) as m,
               count(s) as deals,
               coalesce(sum(s.finalPrice),0) as revenue,
               coalesce(sum(s.finalPrice - coalesce(c.purchasePrice,0)),0) as profit
        from Sale s
        join s.car c
        where (:year is null or function('year', s.saleDate) = :year)
        group by function('year', s.saleDate), function('month', s.saleDate)
        order by y asc, m asc
    """)
    List<Object[]> monthly(@Param("year") Integer year);

    /* -------- TOP CARS BY PROFIT -------- */
    @Query("""
        select c.id as carId,
               count(s) as deals,
               coalesce(sum(s.finalPrice),0) as revenue,
               coalesce(sum(s.finalPrice - coalesce(c.purchasePrice,0)),0) as profit
        from Sale s
        join s.car c
        where (:start is null or s.saleDate >= :start)
          and (:end   is null or s.saleDate <= :end)
        group by c.id
        order by profit desc
    """)
    List<Object[]> topCars(@Param("start") LocalDate start,
                           @Param("end")   LocalDate end,
                           Pageable pageable);

    /* -------- TOP CUSTOMERS BY REVENUE (จาก Sale.customerName) -------- */
    @Query("""
        select s.customerName as cname,
               count(s) as deals,
               coalesce(sum(s.finalPrice),0) as revenue,
               coalesce(sum(s.finalPrice - coalesce(c.purchasePrice,0)),0) as profit
        from Sale s
        join s.car c
        where s.customerName is not null
          and (:start is null or s.saleDate >= :start)
          and (:end   is null or s.saleDate <= :end)
        group by s.customerName
        order by revenue desc
    """)
    List<Object[]> topCustomers(@Param("start") LocalDate start,
                                @Param("end")   LocalDate end,
                                Pageable pageable);

    /* -------- PAYMENT METHOD BREAKDOWN -------- */
    @Query("""
        select s.paymentMethod as method,
               count(s) as deals,
               coalesce(sum(s.finalPrice),0) as revenue
        from Sale s
        where s.paymentMethod is not null
          and (:start is null or s.saleDate >= :start)
          and (:end   is null or s.saleDate <= :end)
        group by s.paymentMethod
        order by revenue desc
    """)
    List<Object[]> paymentMethods(@Param("start") LocalDate start,
                                  @Param("end")   LocalDate end);
}

//package com.kiwi.shop.repository;
//
//import com.kiwi.constant.ItemSellStatus;
//import com.querydsl.core.QueryFactory;
//import com.querydsl.core.QueryResults;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.core.types.dsl.Wildcard;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.kiwi.shop.dto.QMainItemDto;
//import com.kiwi.shop.entity.QItem;
//import com.kiwi.shop.entity.QItemImg;
//import com.kiwi.shop.dto.ItemSearchDto;
//import com.kiwi.shop.dto.MainItemDto;
//import com.kiwi.shop.entity.Item;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.thymeleaf.util.StringUtils;
//
//import javax.persistence.EntityManager;
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
//	
//    private JPAQueryFactory queryFactory;
//    
//    public ItemRepositoryCustomImpl(EntityManager em){
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//    
//    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
//        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
//    }
//    
//    private BooleanExpression regDtsAfter(String searchDateType){
//        LocalDateTime dateTime = LocalDateTime.now();
//        
//        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
//            return null;
//        } else if (StringUtils.equals("1d",searchDateType)) {
//            dateTime = dateTime.minusDays(1);
//        }
//        else if (StringUtils.equals("1w",searchDateType)) {
//            dateTime = dateTime.minusWeeks(1);
//        }
//        else if (StringUtils.equals("1m",searchDateType)) {
//            dateTime = dateTime.minusMonths(1);
//        }
//        else if (StringUtils.equals("6m",searchDateType)) {
//            dateTime = dateTime.minusMonths(1);
//        }
//        return QItem.item.regTime.after(dateTime);
//    }
//
//    private BooleanExpression searchByLike(String searchBy, String searchQuery){
//        if(StringUtils.equals("itemNm",searchBy)){
//            return QItem.item.itemNm.like("%" + searchQuery + "%");
//        } else if (StringUtils.equals("createdBy",searchBy)) {
//            return QItem.item.createdBy.like("%" + searchQuery + "%");
//        }
//        return null;
//    }
//
//    @Override
//    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
//// queryDSL 5.0 ???????????? fetchResults()???  fetchCount()???????????? deprecated ????????? ????????? count ?????? ?????? ????????? ?????? ??????????????????
////        QueryResults<Item> results = queryFactory
////                .selectFrom(QItem.item)
////                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
////                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
////                        searchByLike(itemSearchDto.getSearchBy(),itemSearchDto.getSearchQuery()))
////                .orderBy(QItem.item.id.desc())
////                .offset(pageable.getOffset())
////                .limit(pageable.getPageSize())
////                .fetchResults();
////
////        List<Item> content = results.getResults();
////        long total = results.getTotal();
////        return new PageImpl<>(content,pageable,total);
//        List<Item> content = queryFactory
//                .selectFrom(QItem.item)
//                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
//                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
//                        searchByLike(itemSearchDto.getSearchBy(),
//                                itemSearchDto.getSearchQuery()))
//                .orderBy(QItem.item.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        long total = queryFactory.select(Wildcard.count).from(QItem.item)
//                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
//                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
//                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
//                .fetchOne()
//                ;
//
//        return new PageImpl<>(content, pageable, total);
//    }
//
//    // ???????????? null??? ????????? ???????????? searchQuery(?????????)??? ????????? ????????? ???????????? ?????? ??????
//    private BooleanExpression itemNmLike(String searchQuery){
//        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");
//    }
//
//
//
//    @Override
//    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
//        QItem item = QItem.item;
//        QItemImg itemImg = QItemImg.itemImg;
//
//        // QMainItemDto???????????? ????????? ????????? ???????????????. @QueryProjection??? ???????????? DTO??? ?????? ?????? ???????????????.
//        List<MainItemDto> content = queryFactory
//                .select(
//                        new QMainItemDto(
//                                item.id,
//                                item.itemNm,
//                                item.itemDetail,
//                                itemImg.imgUrl,
//                                item.price)
//                )
//                .from(itemImg)
//                .join(itemImg.item, item)       // itemImg??? item??? ?????? ???????????????
//                .where(itemImg.repimgYn.eq("Y"))        // ?????? ???????????? ?????? ?????? ?????? ???????????? ???????????????.
//                .where(itemNmLike(itemSearchDto.getSearchQuery()))
//                .orderBy(item.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        long total = queryFactory
//                .select(Wildcard.count)
//                .from(itemImg)
//                .join(itemImg.item, item)
//                .where(itemImg.repimgYn.eq("Y"))
//                .where(itemNmLike(itemSearchDto.getSearchQuery()))
//                .fetchOne()
//                ;
//
//        return new PageImpl<>(content, pageable, total);
//    }
//}

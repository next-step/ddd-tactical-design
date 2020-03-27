package kitchenpos.menus.tobe.domain.menu.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuProducts {
    private List<MenuProductVO> menuProducts;

    public MenuProducts(){
        this(Collections.emptyList());
    }

    public MenuProducts(List<MenuProductVO> menuProductVOS) {
        this.menuProducts = new ArrayList<>(menuProductVOS);
    }

    public void add (MenuProductVO menuProductVO){
        validateProductDuplicate(menuProductVO);
        this.menuProducts.add(menuProductVO);
    }

    public List<MenuProductVO> list(){
        return new ArrayList<>(this.menuProducts);
    }

    public void compare (BigDecimal menuPrice){
        System.out.println(menuPrice + ", " + this.totalAcount());
        if(menuPrice.compareTo(this.totalAcount()) > 0){
            throw new WrongMenuPriceException("메뉴가격을 잘못 설정했습니다.");
        }
    }

    private BigDecimal totalAcount (){
        return menuProducts.stream()
            .map(menuProduct -> menuProduct.getPrice().multiply(new BigDecimal(menuProduct.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validateProductDuplicate (MenuProductVO newVO){
        menuProducts.stream()
            .forEach(menuProduct ->{
                if(menuProduct.getProductId() == newVO.getProductId()){
                    throw new WrongInputProductException ("새 메뉴에 상품을 중복으로 입력했습니다.");
                }
            });
    }

}

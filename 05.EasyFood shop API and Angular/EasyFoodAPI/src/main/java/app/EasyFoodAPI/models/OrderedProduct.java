package app.EasyFoodAPI.models;
import javax.persistence.*;


@Entity
@Table(name = "ordered_products")
public class OrderedProduct {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "count")
    private Float count;

    @Column(name = "general_price")
    private Float generalPrice;



    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Float getGeneralPrice() {
        return generalPrice;
    }

    public void setGeneralPrice(Float generalPrice) {
        this.generalPrice = generalPrice;
    }
}

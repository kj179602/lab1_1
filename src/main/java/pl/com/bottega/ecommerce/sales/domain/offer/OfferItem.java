/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;


public class OfferItem {

	// product
	private Product product;

	private int quantity;

	private Money totalCost;

	private Discount discount;

	public OfferItem(Product product, int quantity) {
        this(product, quantity, null, null);
    }

	public OfferItem(Product product, int quantity, Discount discount, Money totalCost) {
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;

        BigDecimal cost = product.getProductPrice().getValue().multiply(new BigDecimal(quantity));
        if (discount.getDiscount().getValue() != null) {
            cost = cost.divide(discount.getDiscount().getValue());
        }
        this.totalCost = new Money(product.getProductPrice().getCurrency(), cost);
    }

	
	public Product getProduct() {
        return product;
    }

    public Discount getDiscount() {
        return discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((discount == null) ? 0 : discount.hashCode());
        result = prime * result + ((product.getProductName() == null) ? 0 : product.getProductName().hashCode());
        result = prime * result + ((product.getProductPrice() == null) ? 0 : product.getProductPrice().hashCode());
        result = prime * result
                + ((product.getProductId() == null) ? 0 : product.getProductId().hashCode());
        result = prime * result + ((product.getProductType() == null) ? 0 : product.getProductType().hashCode());
        result = prime * result + quantity;
        result = prime * result
                + ((totalCost == null) ? 0 : totalCost.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OfferItem other = (OfferItem) obj;
        if (discount == null) {
            if (other.discount != null) {
                return false;
            }
        } else if (!discount.equals(other.discount)) {
            return false;
        }
        if (product.getProductName() == null) {
            if (other.getProduct().getProductName() != null) {
                return false;
            }
        } else if (!product.getProductName().equals(other.getProduct().getProductName())) {
            return false;
        }
        if (product.getProductPrice() == null) {
            if (other.getProduct().getProductPrice() != null) {
                return false;
            }
        } else if (!product.getProductPrice().equals(other.getProduct().getProductPrice())) {
            return false;
        }
        if (product.getProductId() == null) {
            if (other.getProduct().getProductId() != null) {
                return false;
            }
        } else if (!product.getProductId().equals(other.getProduct().getProductId())) {
            return false;
        }
        if (product.getProductType() != other.getProduct().getProductType()) {
            return false;
        }
        if (quantity != other.quantity) {
            return false;
        }
        if (totalCost == null) {
            if (other.totalCost != null) {
                return false;
            }
        } else if (!totalCost.equals(other.totalCost)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param item
     * @param delta acceptable percentage difference
     * @return
     */
    public boolean sameAs(OfferItem other, double delta) {
        if (product.getProductName() == null) {
            if (other.getProduct().getProductName() != null) {
                return false;
            }
        } else if (!product.getProductName().equals(other.getProduct().getProductName())) {
            return false;
        }
        if (product.getProductPrice() == null) {
            if (other.getProduct().getProductPrice() != null) {
                return false;
            }
        } else if (!product.getProductPrice().equals(other.getProduct().getProductPrice())) {
            return false;
        }
        if (product.getProductId() == null) {
            if (other.getProduct().getProductId() != null) {
                return false;
            }
        } else if (!product.getProductId().equals(other.getProduct().getProductId())) {
            return false;
        }
        if (product.getProductType() != other.getProduct().getProductType()) {
            return false;
        }

        if (quantity != other.getQuantity()) {
            return false;
        }
        if (totalCost.getCurrency() != other.getTotalCost().getCurrency()) {
            return false;
        }
        BigDecimal max, min;
        if (totalCost.getValue().compareTo(other.getTotalCost().getValue()) > 0) {
            max = totalCost.getValue();
            min = other.getTotalCost().getValue();
        } else {
            max = other.getTotalCost().getValue();
            min = totalCost.getValue();
        }

        BigDecimal difference = max.subtract(min);
        BigDecimal acceptableDelta = max.multiply(new BigDecimal(delta / 100));

        return acceptableDelta.compareTo(difference) > 0;
    }

}

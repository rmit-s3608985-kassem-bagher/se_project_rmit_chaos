<?php

/**
 * Created by PhpStorm.
 * User: kassem
 * Date: 17/4/17
 * Time: 7:31 PM
 *
 * @access protected
 */
class product
{

    /**
     * @access private
     * @param $product_id
     */
    public function increaseStockLevel($con, $product_id, $quantity)
    {
        if ($con == null)
            $con = mysqli_connect('localhost', 'root', '', 'supermarket');

        $con->set_charset("utf8");
        $result = mysqli_query($con, "update product set prod_stock_level =prod_stock_level+$quantity where prod_id=$product_id");
        if (!$result)
            return false;
        return true;
    }

    private function getDiscount($product_id, $percentage, $quantity)
    {
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "select * from discount where disc_quantity=$quantity and disc_percentage=$percentage and product = $product_id");
        $row = $result->fetch_assoc();

        if ($row == null) {
            $res = new stdClass();
            $res->discount = null;
            return $res;
        }

        $discount = new stdClass();
        $discount->quantity = $row['disc_quantity'];
        $discount->percentage = $row['disc_percentage'];
        $res = new stdClass();
        $res->discount = $discount;
        return $res;
    }

    /**
     * @url POST /{product_id}/delete_discount
     * @param $product_id
     * @param $percentage
     * @param $quantity
     * @return stdClass
     */
    public function deleteDiscount($product_id, $percentage, $quantity)
    {
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "delete from discount where disc_quantity=$quantity and disc_percentage=$percentage and product=$product_id");
        if (!$result)
            throw new RestException(400, "could not delete discount");

        $disc = $this->getDiscount($product_id, $percentage, $quantity);
        if ($disc->discount!=null)
            throw new RestException(400, "could not delete discount.");

        $responce = new stdClass();
        $responce->code = "200";
        $responce->message = "discount deleted";
        $res = new stdClass();
        $res->responce = $responce;
        return $res;
    }

    /**
     * @url POST /{product_id}/add_discount
     * @param $product_id
     * @param $percentage
     * @param $quantity
     * @return stdClass
     */
    public function addDiscount($product_id, $percentage, $quantity)
    {
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "insert into discount (disc_quantity, product, disc_percentage) values($quantity,$product_id,$percentage) ");
        if (!$result)
            throw new RestException(400, "could not add discount");

        $disc = $this->getDiscount($product_id, $percentage, $quantity);
        if ($disc->discount==null)
            throw new RestException(400, "could not add discount.");

        $responce = new stdClass();
        $responce->code = "200";
        $responce->message = "discount added";
        $res = new stdClass();
        $res->responce = $responce;
        return $res;
    }


    /**
     * @url POST /{product_id}/update_discount
     * @param $product_id
     * @param $old_percentage
     * @param $old_quantity
     * @param $new_percentage
     * @param $new_quantity
     * @return updated discount
     */
    public function editDiscount($product_id, $old_percentage, $old_quantity, $new_percentage, $new_quantity)
    {
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "update discount set disc_percentage=$new_percentage,disc_quantity=$new_quantity where disc_quantity=$old_quantity and disc_percentage=$old_percentage and product=$product_id");
        if (!$result)
            throw new RestException(400, "could not update discount");

        return $this->getDiscount($product_id, $new_percentage, $new_quantity);
    }

    /**
     * @url GET /
     * @return array
     */
    public function listAllProducts()
    {
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "select * from product");
        $products = array();
        while ($row = mysqli_fetch_array($result)) {
            $prod = array(
                'id' => $row['prod_id'],
                'name' => $row['prod_name'],
                'unit_price' => $row['prod_unit_price'],
                'stock_level' => $row['prod_stock_level'],
                'replenish_level' => $row['prod_replenish_level'],
                'type' => $row['prod_type'],
                'discounts' => $this->getProductDiscounts($row['prod_id']));
            $products[] = $prod;
        }
        mysqli_close($con);
        return $products;
    }

    /**
     * @url GET /discounts
     * @url GET /{product_id}/discounts
     * @param $product_id product id
     * @return list of discounts
     */
    public function getProductDiscounts($product_id)
    {
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "select * from discount where product=$product_id");
        $discounts = array();
        while ($row = mysqli_fetch_array($result)) {
            $disc = array(
                'quantity' => $row['disc_quantity'],
                'percentage' => $row['disc_percentage']);
            $discounts[] = $disc;
        }
        mysqli_close($con);
        return $discounts;
    }
}
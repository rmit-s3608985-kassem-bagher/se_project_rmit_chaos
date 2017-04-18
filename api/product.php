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
    public function increaseStockLevel($con,$product_id, $quantity)
    {
        if ($con == null)
            $con = mysqli_connect('localhost', 'root', '', 'supermarket');

        $con->set_charset("utf8");
        $result = mysqli_query($con,"update product set prod_stock_level =prod_stock_level+$quantity where prod_id=$product_id");
        if (!$result)
            return false;
        return true;
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
<?php

/**
 * Created by PhpStorm.
 * User: kassem
 * Date: 17/4/17
 * Time: 7:31 PM
 */
class supplier
{
    /**
     * @url GET /{suppiler_id}/products
     * @url GET /products
     * @param $suppiler_id supplier id
     * @return list of suppliers products
     */
    public function getSupplierProducts($suppiler_id){
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "select * from product where supplier = $suppiler_id");
        $products = array();
        while($row = mysqli_fetch_array($result))
        {
            $prod = array(
                'id' => $row['prod_id'],
                'name' => $row['prod_name'],
                'unit_price' => $row['prod_unit_price'],
                'stock_level' => $row['prod_stock_level'],
                'replenish_level' => $row['prod_replenish_level'],
                'type' => $row['prod_type'],
                'discounts' => product::getProductDiscounts($row['prod_id']));
            $products[] = $prod;
        }
        return $products;
    }

    /**
     * @url GET /
     * @return list of suppliers
     */
    public function index(){
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "select * from supplier");
        $suppliers = array();

        while($row = mysqli_fetch_array($result))
        {
            $sup = array(
                'id' => $row['sup_id'],
                'name' => $row['sup_name'],
                'address' => $row['sup_address'],
                'postcode' => $row['sup_postcode'],
                'phone' => $row['sup_phone'],
                'products' => $this->getSupplierProducts($row['sup_id']));
            $suppliers[] = $sup;
        }
        return $suppliers;

    }
}
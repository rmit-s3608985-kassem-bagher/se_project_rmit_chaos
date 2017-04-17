<?php

/**
 * Created by PhpStorm.
 * User: kassem
 * Date: 17/4/17
 * Time: 7:31 PM
 */
class supplier
{
    private function getSupplierProduct($suppiler_id){

    }

    public function index(){
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "select * from supplier");

        $suppliers = null;

        while($row = mysqli_fetch_array($result))
        {
            $sup = array(
                'id' => $row['sup_id'],
                'name' => $row['sup_name'],
                'address' => $row['sup_address'],
                'postcode' => $row['sup_postcode'],
                'phone' => $row['sup_phone']);
            $suppliers[] = $sup;
        }
        return $suppliers;

    }
}
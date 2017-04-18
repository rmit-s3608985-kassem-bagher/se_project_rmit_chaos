<?php

/**
 * Created by PhpStorm.
 * User: kassem
 * Date: 17/4/17
 * Time: 7:33 PM
 *
 * @access protected
 */
class purchaseorder
{

    private function addPurchaseOrderItemRecord($con, $purchase_order_id, $product_id, $quantity, $total)
    {
        $query = "insert into purchase_order_item (purchase_order, product, item_quantity,item_total) values($purchase_order_id,$product_id,$quantity,$total) ";
        $result = mysqli_query($con, $query);
        if (!$result)
            return false;
        if(!product::increaseStockLevel($con,$product_id,$quantity))
            return false;
        return true;
    }

    private function addPurchaseOrderRecord($con, $date, $employee_id, $supplier_id)
    {
        $result = mysqli_query($con, "insert into purchase_order (prd_date, employee, supplier) values ('$date','$employee_id','$supplier_id')");
        if ($result)
            return $con->insert_id;
        return 0;
    }

    /**
     *
     * @url POST /
     * @param $request_data jason body
     */
    public function index($request_data)
    {
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        mysqli_autocommit($con, false);
        // add purchase order record
        $result = $this->addPurchaseOrderRecord($con, $request_data["date"], $request_data["employee"]["id"], $request_data["supplier"]["id"]);
        if ($result == 0) {
            mysqli_rollback($con);
            mysqli_close($con);
            throw new RestException(400, 'could not add Purchase Order');
        }

        // add purchase prder item records
        $purchase_order_id = $result;
        foreach ($request_data["orderItems"] as $item) {
            $result = $this->addPurchaseOrderItemRecord($con, $purchase_order_id,$item["product"]["id"],$item["quantity"],$item["total"]);
            if (!$result) {
                mysqli_rollback($con);
                mysqli_close($con);
                throw new RestException(400, 'could not add Purchase Order Item');
            }
        }
        mysqli_commit($con);
        mysqli_close($con);


        $responce = new stdClass();
        $responce->code = "200";
        $responce->message = "Prchase order placed";
        $res = new stdClass();
        $res->responce = $responce;
        return $res;
    }
}
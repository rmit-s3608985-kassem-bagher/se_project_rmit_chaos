<?php

require_once '../restler.php';

use Luracast\Restler\Restler;
$r = new Restler();
$r->setSupportedFormats('JsonFormat');
$r->addAPIClass('employee');
$r->addAPIClass('customer');
$r->handle();
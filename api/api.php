<?php

require_once '../restler.php';

$r = new Restler(true);
$r->setSupportedFormats('JsonFormat');
$r->addAPIClass('customer');
$r->handle();
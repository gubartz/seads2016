<?php
// Routes

$app->get('/professor/list', function ($request, $response) {

	$db = $this->db;
	foreach($db->query('SELECT * FROM v_professor') as $row){
		//dados do professor
		$result['id']         = $row['professor_id'];
		$result['prontuario'] = $row['prontuario'];
		$result['nome']       = $row['nome'];
		
		//dados da titulação do professor
		$result['titulacao']['titulacao_id']  = $row['titulacao_id'];
		$result['titulacao']['titulo']        = $row['titulo'];

		$return[] = $result;
	};

	return $response->withJson($return);
});

$app->get('/titulacao/list', function ($request, $response) {

	$db = $this->db;
	foreach($db->query('SELECT * FROM titulacao') as $row){
		//dados da titulação
		$result['id']        = $row['id'];
		$result['descricao'] = $row['descricao'];

		$return[] = $result;
	};

	return $response->withJson($return);
});

$app->post('/professor/new', function ($request, $response) {
	$base_photo_url = __DIR__ . DIRECTORY_SEPARATOR . '..' . DIRECTORY_SEPARATOR . 'public' . DIRECTORY_SEPARATOR . 'images' . DIRECTORY_SEPARATOR;

	$dados = $request->getParsedBody();

	$foto = $dados['foto'];

	$db = $this->db;
 	$sth = $db->prepare("INSERT INTO professor(prontuario, nome, titulacao_id) VALUES (:prontuario, :nome, :titulacao_id)");

 	$professor    = json_decode($dados['professor'], true);
  $titulacao_id = $professor['titulacao']['id'];

  $insertProfessor['nome']         = $professor['nome'];
  $insertProfessor['prontuario']   = $professor['prontuario'];
  $insertProfessor['titulacao_id'] = $titulacao_id;

	$sth->execute($insertProfessor);

 	$lastInsertId = $db->lastInsertId('professor_id_seq');
 	$insertProfessor['id'] = $lastInsertId;
	 	
 	$imgFileName = "{$base_photo_url}{$lastInsertId}.png";
 	$file = fopen($imgFileName, "w");
 	fwrite($file, $foto); 	

 	return $response->withJson($insertProfessor);
});
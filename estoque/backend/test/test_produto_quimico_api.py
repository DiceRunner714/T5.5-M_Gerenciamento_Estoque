from fastapi.testclient import TestClient
from main import app

client = TestClient(app)


def criar_empresa_e_filial():
    empresa = client.post("/empresa/", json={"nome": "Empresa Teste"}).json()
    filial = client.post("/filial/", json={
        "nome": "Filial Teste",
        "local": "São Paulo",
        "empresa_id": empresa["id"]
    }).json()
    return empresa["id"], filial["id"]


def test_criar_produto_quimico():
    empresa_id, filial_id = criar_empresa_e_filial()

    response = client.post("/produto_quimico/", json={
        "nome": "Remédio",
        "categoria": "Analgésico",
        "valor": 12.50,
        "quantidade": 10,
        "toxicidade": 3,
        "filial_id": filial_id
    })
    assert response.status_code == 200
    data = response.json()
    assert data["nome"] == "Remédio"
    assert data["quantidade"] == 10
    assert data["restrito"] in [True, False]

    client.delete(f"/produto_quimico/{data['id']}")
    client.delete(f"/filial/{filial_id}")
    client.delete(f"/empresa/{empresa_id}")


def test_listar_produtos_quimicos():
    empresa_id, filial_id = criar_empresa_e_filial()
    produto = client.post("/produto_quimico/", json={
        "nome": "Produto Listagem",
        "categoria": "Teste",
        "valor": 5.0,
        "quantidade": 1,
        "toxicidade": 1,
        "filial_id": filial_id
    }).json()

    response = client.get("/produto_quimico/")
    assert response.status_code == 200
    assert isinstance(response.json(), list)
    assert any(p["id"] == produto["id"] for p in response.json())

    client.delete(f"/produto_quimico/{produto['id']}")
    client.delete(f"/filial/{filial_id}")
    client.delete(f"/empresa/{empresa_id}")


def test_buscar_produto_quimico_por_id():
    empresa_id, filial_id = criar_empresa_e_filial()
    post = client.post("/produto_quimico/", json={
        "nome": "Busca PQ",
        "categoria": "Teste",
        "valor": 8.99,
        "quantidade": 5,
        "toxicidade": 4,
        "filial_id": filial_id
    }).json()

    response = client.get(f"/produto_quimico/{post['id']}")
    assert response.status_code == 200
    assert response.json()["nome"] == "Busca PQ"

    client.delete(f"/produto_quimico/{post['id']}")
    client.delete(f"/filial/{filial_id}")
    client.delete(f"/empresa/{empresa_id}")


def test_deletar_produto_quimico():
    empresa_id, filial_id = criar_empresa_e_filial()
    post = client.post("/produto_quimico/", json={
        "nome": "Para Deletar",
        "categoria": "Teste",
        "valor": 6.0,
        "quantidade": 3,
        "toxicidade": 2,
        "filial_id": filial_id
    }).json()

    response = client.delete(f"/produto_quimico/{post['id']}")
    assert response.status_code == 200
    assert response.json()["mensagem"] == "Produto removido com sucesso"

    client.delete(f"/filial/{filial_id}")
    client.delete(f"/empresa/{empresa_id}")

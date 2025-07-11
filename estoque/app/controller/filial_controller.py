from sqlalchemy.orm import Session
from config.database import SessionLocal
from model.filial_orm import FilialORM
from model.produto_quimico_orm import ProdutoQuimicoORM
from model.farmaceutico_orm import FarmaceuticoORM


def criar_filial(nome: str, local: str, empresa_id: int) -> FilialORM:
    db: Session = SessionLocal()
    nova = FilialORM(nome=nome, local=local, empresa_id=empresa_id)
    db.add(nova)
    db.commit()
    db.refresh(nova)
    return nova


def listar_filiais() -> list[FilialORM]:
    db: Session = SessionLocal()
    return db.query(FilialORM).all()


def buscar_filial_por_id(id: int) -> FilialORM | None:
    db: Session = SessionLocal()
    return db.query(FilialORM).filter(FilialORM.id == id).first()


def remover_filial(id: int) -> bool:
    db: Session = SessionLocal()
    filial = db.query(FilialORM).filter(FilialORM.id == id).first()
    if filial:
        db.delete(filial)
        db.commit()
        return True
    return False


def calcular_estoque_total_filial(filial_id: int):
    db: Session = SessionLocal()

    produtos = db.query(ProdutoQuimicoORM).filter(
        ProdutoQuimicoORM.filial_id == filial_id
    ).all()

    farmaceuticos = db.query(FarmaceuticoORM).filter(
        FarmaceuticoORM.filial_id == filial_id
    ).all()

    total_estoque = (
        sum(p.quantidade for p in produtos)
        + sum(f.quantidade for f in farmaceuticos)
    )

    lista_produtos = [
        {
            "id": produto.id,
            "nome": produto.nome,
            "quantidade": produto.quantidade,
            "categoria": produto.categoria
        } for produto in produtos
    ]

    lista_farmaceuticos = [
        {
            "id": farmaceutico.id,
            "nome": farmaceutico.nome,
            "quantidade": farmaceutico.quantidade,
            "categoria": farmaceutico.categoria
        } for farmaceutico in farmaceuticos
    ]

    return {
        "filial_id": filial_id,
        "estoque_total": total_estoque,
        "produtos": lista_produtos,
        "farmaceuticos": lista_farmaceuticos
    }

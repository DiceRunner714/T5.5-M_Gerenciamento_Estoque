from sqlalchemy.orm import Session
from config.database import SessionLocal
from model.produto_quimico_orm import ProdutoQuimicoORM
from schema.produto_quimico_schema import ProdutoQuimicoUpdate


def criar_produto_quimico(dados) -> ProdutoQuimicoORM:
    db: Session = SessionLocal()
    restrito = dados.toxicidade >= 7
    novo = ProdutoQuimicoORM(
        nome=dados.nome,
        categoria=dados.categoria,
        valor=dados.valor,
        quantidade=dados.quantidade,
        toxicidade=dados.toxicidade,
        restrito=restrito,
        filial_id=dados.filial_id
    )
    db.add(novo)
    db.commit()
    db.refresh(novo)
    return novo


def listar_produtos() -> list[ProdutoQuimicoORM]:
    db: Session = SessionLocal()
    return db.query(ProdutoQuimicoORM).all()


def buscar_produto_por_id(id: int) -> ProdutoQuimicoORM | None:
    db: Session = SessionLocal()
    return db.query(ProdutoQuimicoORM).filter(ProdutoQuimicoORM.id == id).first()


def buscar_produto_por_nome(nome: str) -> list[ProdutoQuimicoORM]:
    db: Session = SessionLocal()
    return db.query(ProdutoQuimicoORM).filter(ProdutoQuimicoORM.nome.ilike(f"%{nome}%")).all()


def remover_produto(id: int) -> bool:
    db: Session = SessionLocal()
    produto = db.query(ProdutoQuimicoORM).filter(ProdutoQuimicoORM.id == id).first()
    if produto:
        db.delete(produto)
        db.commit()
        return True
    return False


def atualizar_produto_quimico(id: int, dados: ProdutoQuimicoUpdate) -> ProdutoQuimicoORM | None:
    db = SessionLocal()
    produto = db.query(ProdutoQuimicoORM).filter_by(id=id).first()
    if not produto:
        return None

    for campo, valor in dados.dict(exclude_unset=True).items():
        setattr(produto, campo, valor)

    produto.restrito = produto.toxicidade >= 7
    db.commit()
    db.refresh(produto)
    return produto

from sqlalchemy.orm import Session
from config.database import SessionLocal
from model.filial_orm import FilialORM

def criar_filial(nome: str, empresa_id: int) -> FilialORM:
    db: Session = SessionLocal()
    nova = FilialORM(nome=nome, empresa_id=empresa_id)
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

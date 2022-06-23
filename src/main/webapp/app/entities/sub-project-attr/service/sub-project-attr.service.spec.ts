import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISubProjectAttr, SubProjectAttr } from '../sub-project-attr.model';

import { SubProjectAttrService } from './sub-project-attr.service';

describe('SubProjectAttr Service', () => {
  let service: SubProjectAttrService;
  let httpMock: HttpTestingController;
  let elemDefault: ISubProjectAttr;
  let expectedResult: ISubProjectAttr | ISubProjectAttr[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubProjectAttrService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      attrCode: 'AAAAAAA',
      attrCodeNameAr: 'AAAAAAA',
      attrCodeNameEn: 'AAAAAAA',
      attrType: 'AAAAAAA',
      attrValue: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a SubProjectAttr', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SubProjectAttr()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubProjectAttr', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          attrCode: 'BBBBBB',
          attrCodeNameAr: 'BBBBBB',
          attrCodeNameEn: 'BBBBBB',
          attrType: 'BBBBBB',
          attrValue: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SubProjectAttr', () => {
      const patchObject = Object.assign(
        {
          attrCode: 'BBBBBB',
          attrCodeNameAr: 'BBBBBB',
        },
        new SubProjectAttr()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubProjectAttr', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          attrCode: 'BBBBBB',
          attrCodeNameAr: 'BBBBBB',
          attrCodeNameEn: 'BBBBBB',
          attrType: 'BBBBBB',
          attrValue: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a SubProjectAttr', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSubProjectAttrToCollectionIfMissing', () => {
      it('should add a SubProjectAttr to an empty array', () => {
        const subProjectAttr: ISubProjectAttr = { id: 123 };
        expectedResult = service.addSubProjectAttrToCollectionIfMissing([], subProjectAttr);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subProjectAttr);
      });

      it('should not add a SubProjectAttr to an array that contains it', () => {
        const subProjectAttr: ISubProjectAttr = { id: 123 };
        const subProjectAttrCollection: ISubProjectAttr[] = [
          {
            ...subProjectAttr,
          },
          { id: 456 },
        ];
        expectedResult = service.addSubProjectAttrToCollectionIfMissing(subProjectAttrCollection, subProjectAttr);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubProjectAttr to an array that doesn't contain it", () => {
        const subProjectAttr: ISubProjectAttr = { id: 123 };
        const subProjectAttrCollection: ISubProjectAttr[] = [{ id: 456 }];
        expectedResult = service.addSubProjectAttrToCollectionIfMissing(subProjectAttrCollection, subProjectAttr);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subProjectAttr);
      });

      it('should add only unique SubProjectAttr to an array', () => {
        const subProjectAttrArray: ISubProjectAttr[] = [{ id: 123 }, { id: 456 }, { id: 49513 }];
        const subProjectAttrCollection: ISubProjectAttr[] = [{ id: 123 }];
        expectedResult = service.addSubProjectAttrToCollectionIfMissing(subProjectAttrCollection, ...subProjectAttrArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subProjectAttr: ISubProjectAttr = { id: 123 };
        const subProjectAttr2: ISubProjectAttr = { id: 456 };
        expectedResult = service.addSubProjectAttrToCollectionIfMissing([], subProjectAttr, subProjectAttr2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subProjectAttr);
        expect(expectedResult).toContain(subProjectAttr2);
      });

      it('should accept null and undefined values', () => {
        const subProjectAttr: ISubProjectAttr = { id: 123 };
        expectedResult = service.addSubProjectAttrToCollectionIfMissing([], null, subProjectAttr, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subProjectAttr);
      });

      it('should return initial array if no SubProjectAttr is added', () => {
        const subProjectAttrCollection: ISubProjectAttr[] = [{ id: 123 }];
        expectedResult = service.addSubProjectAttrToCollectionIfMissing(subProjectAttrCollection, undefined, null);
        expect(expectedResult).toEqual(subProjectAttrCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

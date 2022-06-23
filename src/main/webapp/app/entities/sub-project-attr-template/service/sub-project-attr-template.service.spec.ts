import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISubProjectAttrTemplate, SubProjectAttrTemplate } from '../sub-project-attr-template.model';

import { SubProjectAttrTemplateService } from './sub-project-attr-template.service';

describe('SubProjectAttrTemplate Service', () => {
  let service: SubProjectAttrTemplateService;
  let httpMock: HttpTestingController;
  let elemDefault: ISubProjectAttrTemplate;
  let expectedResult: ISubProjectAttrTemplate | ISubProjectAttrTemplate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubProjectAttrTemplateService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      attrCode: 'AAAAAAA',
      attrCodeNameAr: 'AAAAAAA',
      attrCodeNameEn: 'AAAAAAA',
      attrType: 'AAAAAAA',
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

    it('should create a SubProjectAttrTemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SubProjectAttrTemplate()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubProjectAttrTemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          attrCode: 'BBBBBB',
          attrCodeNameAr: 'BBBBBB',
          attrCodeNameEn: 'BBBBBB',
          attrType: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SubProjectAttrTemplate', () => {
      const patchObject = Object.assign(
        {
          attrCode: 'BBBBBB',
          attrType: 'BBBBBB',
        },
        new SubProjectAttrTemplate()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubProjectAttrTemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          attrCode: 'BBBBBB',
          attrCodeNameAr: 'BBBBBB',
          attrCodeNameEn: 'BBBBBB',
          attrType: 'BBBBBB',
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

    it('should delete a SubProjectAttrTemplate', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSubProjectAttrTemplateToCollectionIfMissing', () => {
      it('should add a SubProjectAttrTemplate to an empty array', () => {
        const subProjectAttrTemplate: ISubProjectAttrTemplate = { id: 123 };
        expectedResult = service.addSubProjectAttrTemplateToCollectionIfMissing([], subProjectAttrTemplate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subProjectAttrTemplate);
      });

      it('should not add a SubProjectAttrTemplate to an array that contains it', () => {
        const subProjectAttrTemplate: ISubProjectAttrTemplate = { id: 123 };
        const subProjectAttrTemplateCollection: ISubProjectAttrTemplate[] = [
          {
            ...subProjectAttrTemplate,
          },
          { id: 456 },
        ];
        expectedResult = service.addSubProjectAttrTemplateToCollectionIfMissing(subProjectAttrTemplateCollection, subProjectAttrTemplate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubProjectAttrTemplate to an array that doesn't contain it", () => {
        const subProjectAttrTemplate: ISubProjectAttrTemplate = { id: 123 };
        const subProjectAttrTemplateCollection: ISubProjectAttrTemplate[] = [{ id: 456 }];
        expectedResult = service.addSubProjectAttrTemplateToCollectionIfMissing(subProjectAttrTemplateCollection, subProjectAttrTemplate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subProjectAttrTemplate);
      });

      it('should add only unique SubProjectAttrTemplate to an array', () => {
        const subProjectAttrTemplateArray: ISubProjectAttrTemplate[] = [{ id: 123 }, { id: 456 }, { id: 20535 }];
        const subProjectAttrTemplateCollection: ISubProjectAttrTemplate[] = [{ id: 123 }];
        expectedResult = service.addSubProjectAttrTemplateToCollectionIfMissing(
          subProjectAttrTemplateCollection,
          ...subProjectAttrTemplateArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subProjectAttrTemplate: ISubProjectAttrTemplate = { id: 123 };
        const subProjectAttrTemplate2: ISubProjectAttrTemplate = { id: 456 };
        expectedResult = service.addSubProjectAttrTemplateToCollectionIfMissing([], subProjectAttrTemplate, subProjectAttrTemplate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subProjectAttrTemplate);
        expect(expectedResult).toContain(subProjectAttrTemplate2);
      });

      it('should accept null and undefined values', () => {
        const subProjectAttrTemplate: ISubProjectAttrTemplate = { id: 123 };
        expectedResult = service.addSubProjectAttrTemplateToCollectionIfMissing([], null, subProjectAttrTemplate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subProjectAttrTemplate);
      });

      it('should return initial array if no SubProjectAttrTemplate is added', () => {
        const subProjectAttrTemplateCollection: ISubProjectAttrTemplate[] = [{ id: 123 }];
        expectedResult = service.addSubProjectAttrTemplateToCollectionIfMissing(subProjectAttrTemplateCollection, undefined, null);
        expect(expectedResult).toEqual(subProjectAttrTemplateCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

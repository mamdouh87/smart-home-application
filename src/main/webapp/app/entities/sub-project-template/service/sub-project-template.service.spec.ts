import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISubProjectTemplate, SubProjectTemplate } from '../sub-project-template.model';

import { SubProjectTemplateService } from './sub-project-template.service';

describe('SubProjectTemplate Service', () => {
  let service: SubProjectTemplateService;
  let httpMock: HttpTestingController;
  let elemDefault: ISubProjectTemplate;
  let expectedResult: ISubProjectTemplate | ISubProjectTemplate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubProjectTemplateService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      projectTemplateCode: 'AAAAAAA',
      projectTemplateNameAr: 'AAAAAAA',
      projectTemplateNameEn: 'AAAAAAA',
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

    it('should create a SubProjectTemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SubProjectTemplate()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubProjectTemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          projectTemplateCode: 'BBBBBB',
          projectTemplateNameAr: 'BBBBBB',
          projectTemplateNameEn: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SubProjectTemplate', () => {
      const patchObject = Object.assign(
        {
          projectTemplateCode: 'BBBBBB',
          projectTemplateNameEn: 'BBBBBB',
        },
        new SubProjectTemplate()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubProjectTemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          projectTemplateCode: 'BBBBBB',
          projectTemplateNameAr: 'BBBBBB',
          projectTemplateNameEn: 'BBBBBB',
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

    it('should delete a SubProjectTemplate', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSubProjectTemplateToCollectionIfMissing', () => {
      it('should add a SubProjectTemplate to an empty array', () => {
        const subProjectTemplate: ISubProjectTemplate = { id: 123 };
        expectedResult = service.addSubProjectTemplateToCollectionIfMissing([], subProjectTemplate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subProjectTemplate);
      });

      it('should not add a SubProjectTemplate to an array that contains it', () => {
        const subProjectTemplate: ISubProjectTemplate = { id: 123 };
        const subProjectTemplateCollection: ISubProjectTemplate[] = [
          {
            ...subProjectTemplate,
          },
          { id: 456 },
        ];
        expectedResult = service.addSubProjectTemplateToCollectionIfMissing(subProjectTemplateCollection, subProjectTemplate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubProjectTemplate to an array that doesn't contain it", () => {
        const subProjectTemplate: ISubProjectTemplate = { id: 123 };
        const subProjectTemplateCollection: ISubProjectTemplate[] = [{ id: 456 }];
        expectedResult = service.addSubProjectTemplateToCollectionIfMissing(subProjectTemplateCollection, subProjectTemplate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subProjectTemplate);
      });

      it('should add only unique SubProjectTemplate to an array', () => {
        const subProjectTemplateArray: ISubProjectTemplate[] = [{ id: 123 }, { id: 456 }, { id: 21531 }];
        const subProjectTemplateCollection: ISubProjectTemplate[] = [{ id: 123 }];
        expectedResult = service.addSubProjectTemplateToCollectionIfMissing(subProjectTemplateCollection, ...subProjectTemplateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subProjectTemplate: ISubProjectTemplate = { id: 123 };
        const subProjectTemplate2: ISubProjectTemplate = { id: 456 };
        expectedResult = service.addSubProjectTemplateToCollectionIfMissing([], subProjectTemplate, subProjectTemplate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subProjectTemplate);
        expect(expectedResult).toContain(subProjectTemplate2);
      });

      it('should accept null and undefined values', () => {
        const subProjectTemplate: ISubProjectTemplate = { id: 123 };
        expectedResult = service.addSubProjectTemplateToCollectionIfMissing([], null, subProjectTemplate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subProjectTemplate);
      });

      it('should return initial array if no SubProjectTemplate is added', () => {
        const subProjectTemplateCollection: ISubProjectTemplate[] = [{ id: 123 }];
        expectedResult = service.addSubProjectTemplateToCollectionIfMissing(subProjectTemplateCollection, undefined, null);
        expect(expectedResult).toEqual(subProjectTemplateCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
